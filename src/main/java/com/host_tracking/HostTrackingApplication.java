package com.host_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;

@SpringBootApplication
public class HostTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostTrackingApplication.class, args);
	}

}
