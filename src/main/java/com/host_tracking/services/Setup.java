package com.host_tracking.services;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Service
public class Setup {

    private String obs;
    private String calc;
    private String stream;

    public void openTest() throws IOException {
        try{
            System.setProperty("java.awt.headless", "true/false");
            String url = "https://www.twitch.tv/kndw54";
            Desktop.getDesktop().browse(URI.create(url));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
