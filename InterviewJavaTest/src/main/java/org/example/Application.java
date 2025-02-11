package org.example;

import org.example.domain.NotificationRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Main application class that runs as a CLI expecting a single argument input of a JSON file location.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar webhook-notifier.jar <path-to-json-file>");
            System.exit(1);
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                String jsonFilePath = args[0];
                NotificationRequest payload = JsonFileUtil.readJsonFile(jsonFilePath);

                NotificationSender sender = new NotificationSender(restTemplate());
                sender.sendNotification(payload);
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error reading the JSON file: " + e.getMessage());
                System.exit(1);
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
