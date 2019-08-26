package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // Handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        if (searchType.equals("all")) {
            //Instantiate empty AL of HMs to deliver to search results
            List<HashMap<String, String>> jobs = new ArrayList<>();
            //Get all the jobs in an AL to search in
            List<HashMap<String, String>> allJobs = JobData.findAll();

            //Loop through all jobs to search
            for (int i = 0; i < allJobs.size(); i++) {
                // Check each VALUE in the job details for the search term
                for (Map.Entry<String, String> jobDetails : allJobs.get(i).entrySet()) {
                    // if any value in the job details contains the search term, add the job to the list
                    if (jobDetails.getValue().toLowerCase().contains(searchTerm.toLowerCase())) {
                        jobs.add(allJobs.get(i));
                    }
                }
            }
            model.addAttribute("jobs", jobs);
            model.addAttribute("columns", ListController.columnChoices);
            return "search";
        } else {
            List<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("jobs", jobs);
            model.addAttribute("columns", ListController.columnChoices);
            return "search";
        }
    }
}
