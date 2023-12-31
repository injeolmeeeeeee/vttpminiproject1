package vttp.iss.nus.miniproject1vttp.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.iss.nus.miniproject1vttp.model.Login;
import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.repo.LoginRepo;
import vttp.iss.nus.miniproject1vttp.service.LoginService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepo loginRepo;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String validateUser(@ModelAttribute @Valid Login login, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        String username = login.getUsername();
        List<RaceResult> lastSearchResults = loginService.getLastSearchResults(username);
        loginRepo.cacheSearchResults(username, lastSearchResults);

        session.setAttribute("lastSearchResults", lastSearchResults);
        System.out.println("Last results set in session: " + lastSearchResults);
        return "redirect:/search";
    }
}