package com.springhow.examples.springboot.examples.startupactuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;

@SpringBootApplication
public class ActuatorStartupExampleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ActuatorStartupExampleApplication.class);
        String startupType = System.getProperty("app.startup.implementation.type", "");
        if ("BUFFERING".equals(startupType)) {

            BufferingApplicationStartup applicationStartup = new BufferingApplicationStartup(2048);
            applicationStartup.addFilter(startupStep -> startupStep.getName().startsWith("spring.beans.instantiate"));
            app.setApplicationStartup(applicationStartup);

        } else if ("JFR".equals(startupType)) {
            app.setApplicationStartup(new FlightRecorderApplicationStartup());
        } else {
            app.setApplicationStartup(ApplicationStartup.DEFAULT);
        }

        app.run(args);
    }

}
