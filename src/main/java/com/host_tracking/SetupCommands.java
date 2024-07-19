package com.host_tracking;

import com.host_tracking.services.Setup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

@ShellComponent
public class SetupCommands {

    @Autowired
    private Setup setup;

    @ShellMethod(key = "start")
    public String start() throws IOException {
        String stream = setup.openStream();
        setup.openObs();
        return "Current programsinitialized: " + stream + " and " + " has been openned.";
    }
}
