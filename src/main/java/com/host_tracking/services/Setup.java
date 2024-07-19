package com.host_tracking.services;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class Setup {

    private String calc;
    private String obsPath = "cmd.exe /c start /d \"C:\\Program Files\\obs-studio\\bin\\64bit\" obs64.exe";
    private String[] obsProperty;
    private String stream = "https://www.twitch.tv/kndw54";
    private String[] property = new String[]{"java.awt.headless", "true/false"};


    public String openStream() throws IOException {
        try {
            System.setProperty(property[0], property[1]);
            Desktop.getDesktop().browse(URI.create(stream));
            return "Stream";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openObs() {
        try {
            Process process = Runtime.getRuntime().exec(obsPath);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
