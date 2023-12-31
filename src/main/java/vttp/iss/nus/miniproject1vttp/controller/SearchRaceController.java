package vttp.iss.nus.miniproject1vttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.service.RaceResultsService;

import java.util.List;

@Controller
public class SearchRaceController {

    @Autowired
    private RaceResultsService raceResultsService;

    @GetMapping("/search/race")
    public String showSearchPage(Model model, @RequestParam(name = "year", required = false) String selectedYear,
            @RequestParam(name = "round", required = false) String selectedRound) {

        List<String> availableYears = raceResultsService.getAvailableYear();
        List<String> availableRounds = raceResultsService.getAvailableRounds(selectedYear);

        model.addAttribute("availableYears", availableYears);
        model.addAttribute("availableRounds", availableRounds);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedRound", selectedRound);

        if (selectedYear != null && selectedRound != null) {
            List<RaceResult> raceResults = raceResultsService.getResultsByYearAndRound(selectedYear, selectedRound);
            model.addAttribute("results", raceResults);
            return "raceresults";
        }

        return "searchrace";
    }

    @GetMapping("/search/race/{year}/{round}")
    public String showResultsPage(Model model, @PathVariable String year, @PathVariable String round) {
        List<RaceResult> raceResults = raceResultsService.getResultsByYearAndRound(year, round);
        model.addAttribute("results", raceResults);
        return "raceresults";
    }
}