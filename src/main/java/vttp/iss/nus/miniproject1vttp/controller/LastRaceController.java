package vttp.iss.nus.miniproject1vttp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.service.LastResultsService;


@Controller
public class LastRaceController {

    @Autowired
    private LastResultsService resultsService;

    @GetMapping("/search/lastresult")
    public String showLastResultPage(Model model) {
        List<RaceResult> raceResults = resultsService.getLastResults();
        model.addAttribute("results", raceResults);
        return "lastresult";
    }
}