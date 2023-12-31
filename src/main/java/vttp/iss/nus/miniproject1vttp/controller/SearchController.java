package vttp.iss.nus.miniproject1vttp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpSession;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String showSearchPage(HttpSession session, Model model) {
        return "search";
    }
}