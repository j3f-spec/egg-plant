package com.eggdepot.service;

public class AnalyticService {
    public String runAnalytics() throws Exception{
        ProcessBuilder pb = new ProcessBuilder(
            "python3",
            "src/main/resources/python/analytics.py"
        );

        Process process = pb.start();

        String output = new String(
            process.getInputStream().readAllBytes()
        );

        process.waitFor();
        
        return output;
    }
}
