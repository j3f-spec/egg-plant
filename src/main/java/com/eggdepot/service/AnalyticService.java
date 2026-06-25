package com.eggdepot.service;

import org.springframework.stereotype.Service;

@Service
public class AnalyticService {
    public String runAnalytics(String path) throws Exception{
        ProcessBuilder pb = new ProcessBuilder(
            "python3",
            path
        );

        Process process = pb.start();

        String output = new String(
            process.getInputStream().readAllBytes()
        );

        process.waitFor();
        
        return "report";
    }

    public String runAnalytics() {
        // Call the original method with a default value
        try{
            String output = this.runAnalytics("/frontend/python/analytics.py"); 
            return output;
        }catch(Exception e){
            System.out.println(e);
        }

        return "Analytical report";
    }
}
