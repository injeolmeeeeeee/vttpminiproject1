package vttp.iss.nus.miniproject1vttp.controller;

import jakarta.validation.Valid;
import vttp.iss.nus.miniproject1vttp.model.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    // @Autowired
    // private LoginRepo loginRepo;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String validateUser(@ModelAttribute @Valid Login login, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }
        return "redirect:/search";
    }

}