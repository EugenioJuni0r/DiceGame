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

import com.lunaltas.dicegame.domain.Gamble;
import com.lunaltas.dicegame.service.BetService;
import com.lunaltas.dicegame.service.GambleService;
import com.lunaltas.dicegame.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/gambles")
public class GamblesController {

  @Autowired
  private GambleService gambleService;

  @Autowired
  private UserService userService;

  @Autowired
  private BetService betService;

  @GetMapping("/index")
  public String index(ModelMap model) {
    model.addAttribute("gambles", gambleService.findAll());
    int size = gambleService.findAll().size();
    model.addAttribute("size", size);
    return "/gamble/index";
  }

  @GetMapping("/new")
  public String newGamble(ModelMap model) {
    model.addAttribute("gamble", new Gamble());
    model.addAttribute("users", userService.findAll());
    model.addAttribute("bets", betService.findAll());
    return "/gamble/new";
  }

  @PostMapping("/create")
  public String create(@Valid Gamble gamble, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("users", userService.findAll());
      model.addAttribute("bets", betService.findAll());
      return "/gamble/new";
    }
    gambleService.save(gamble);
    redirectAttributes.addFlashAttribute("success", "Gamble salvo com sucesso");
    return "redirect:/gambles/show/" + gamble.getId();
  }

  @GetMapping("/show/{id}")
  public String show(@PathVariable Long id, ModelMap model) {
    model.addAttribute("gamble", gambleService.findById(id));
    return "/gamble/show";
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, ModelMap model) {
    model.addAttribute("gamble", gambleService.findById(id));
    model.addAttribute("users", userService.findAll());
    model.addAttribute("bets", betService.findAll());
    return "/gamble/edit";
  }

  @PutMapping("/update/{id}")
  public String update(@PathVariable Long id, @Valid Gamble gamble, BindingResult result, ModelMap model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("users", userService.findAll());
      model.addAttribute("bets", betService.findAll());
      return "/gamble/edit";
    }
    gambleService.update(gamble);
    redirectAttributes.addFlashAttribute("success", "Gamble atualizado com sucesso");
    return "redirect:/gambles/show/" + gamble.getId();
  }

  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    gambleService.delete(id);
    redirectAttributes.addFlashAttribute("success", "Gamble deletado com sucesso");
    return "redirect:/gambles/index";
  }
}
