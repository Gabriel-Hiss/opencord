package net.opencord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.opencord.database.Maria;

@SpringBootApplication
public class OpenCordBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenCordBackendApplication.class, args);

        // Initialize the database
        Maria.initialize();
    }

}