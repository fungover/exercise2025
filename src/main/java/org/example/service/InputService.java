package org.example.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class InputService {
    private static final Logger LOGGER = Logger.getLogger(InputService.class.getName());
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine(){
        try {
            return reader.readLine();
        } catch (Exception ex) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Error reading input", ex);
            return "";
        }
    }
}
