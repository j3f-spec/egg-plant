package com.eggdepot.service;

import java.io.IOException;

public class RExecService {
    public String runScript(String path, String inputFile)
    throws IOException{
        ProcessBuilder pb = new ProcessBuilder("Rscript", path, inputFile);

        Process process = pb.start();

        String output = new String(
            process.getInputStream().readAllBytes()
        );

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return output;
    }
}
