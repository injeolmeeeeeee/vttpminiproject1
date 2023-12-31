package vttp.iss.nus.miniproject1vttp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpSession;
import vttp.iss.nus.miniproject1vttp.model.RaceResult;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String showSearchPage(HttpSession session, Model model) {
        List<RaceResult> lastSearchResults = (List<RaceResult>) session.getAttribute("lastSearchResults");
        System.out.println("Last search results retrived in /search: " + lastSearchResults);

        if (lastSearchResults == null || lastSearchResults.isEmpty()) {
            model.addAttribute("noResultsMessage", "No search results available.");
        } else {
            model.addAttribute("lastSearchResults", lastSearchResults);
        }
        return "search";
    }
}