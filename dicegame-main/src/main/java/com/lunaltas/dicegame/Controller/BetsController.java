package com.lunaltas.dicegame.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lunaltas.dicegame.domain.Bet;
import com.lunaltas.dicegame.service.BetService;
import com.lunaltas.dicegame.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bets")
public class BetsController {

  @Autowired
  private BetService betService;

  @Autowired
  private UserService userService;

  @GetMapping("/index")
  public String index(ModelMap model) {
    model.addAttribute("bets", betService.findAll());
    int size = betService.findAll().size();
    model.addAttribute("size", size);
    return "/bets/index";
  }

  @GetMapping("/new")
  public String newBet(ModelMap model) {
    model.addAttribute("bet", new Bet());
    model.addAttribute("users", userService.findAll());
    return "/bets/new";
  }

  @PostMapping("/create")
  public String create(@Valid Bet bet, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) { 
    if (result.hasErrors()) {
      model.addAttribute("users", userService.findAll());
      return "/bets/new";
    }
    betService.save(bet);
    redirectAttributes.addFlashAttribute("success", "Bet salvo com sucesso");
    return "redirect:/bets/show/" + bet.getId();
  }

  @GetMapping("/show/{id}")
  public String show(@PathVariable Long id, ModelMap model) {
    model.addAttribute("bet", betService.findById(id));
    return "/bets/show";
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, ModelMap model) {
    model.addAttribute("bet", betService.findById(id));
    model.addAttribute("users", userService.findAll());
    return "/bets/edit";
  }

  @PutMapping("/update/{id}")
  public String update(@PathVariable Long id, @Valid Bet bet, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("users", userService.findAll());
      return "/bets/edit";
    }
    betService.update(bet);
    redirectAttributes.addFlashAttribute("success", "Bet atualizado com sucesso");
    return "redirect:/bets/show/" + bet.getId();
  }

  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    betService.delete(id);
    redirectAttributes.addFlashAttribute("success", "Bet deletado com sucesso");
    return "redirect:/bets/index";
  }
}
