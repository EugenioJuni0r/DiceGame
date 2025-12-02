package com.lunaltas.dicegame.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
  
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @GetMapping("/")
  public String home() {
    return "/home";
  }
  
 // abrir pagina login
	@GetMapping({"/login"})
	public String login() {
		
		return "login";
	} 	

	// Processa tentativa de login (POST)
	@PostMapping({"/login"})
	public String loginPost(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			return "redirect:/bets/index";
		} catch (AuthenticationException ex) {
			redirectAttributes.addFlashAttribute("alerta", "erro");
			redirectAttributes.addFlashAttribute("titulo", "Credenciais inválidas!");
			redirectAttributes.addFlashAttribute("texto", "Login ou senha incorretos, tente novamente.");
			redirectAttributes.addFlashAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
			return "redirect:/login";
		}
	}

	// login invalido (mantido para compatibilidade)
	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Credenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}	

  @GetMapping("/access-denied")
  public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}  
  
}
