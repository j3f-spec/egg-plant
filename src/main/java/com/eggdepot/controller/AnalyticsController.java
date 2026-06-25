package com.eggdepot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eggdepot.service.AnalyticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    AnalyticService service;

    @GetMapping
    public String run(){
        try{
            return service.runAnalytics();
        }catch(Exception e){
            System.out.println(e);
        }

        return service.runAnalytics();
    }
    
}
