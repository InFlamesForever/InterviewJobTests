package org.example;

import org.example.domain.NotificationContent;
import org.example.domain.NotificationRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


/**
 * Class responsible for sending notifications using HTTP POST requests.
 * It uses a {@link RestTemplate} for HTTP communication.
 * <p>
 * The notification request contains a URL and a body represented by {@link NotificationRequest}.
 * Detailed information such as request body, response body, status code, and response time
 * is logged during the notification process.
 * <p>
 * If the notification fails, the error message and response time are logged.
 */
public class NotificationSender {
    private final RestTemplate restTemplate;

    public NotificationSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(final NotificationRequest request) {
        HttpEntity<NotificationContent> requestEntity = new HttpEntity<>(request.getNotificationContent());

        String url = request.getNotificationUrl();
        System.out.println("Sending notification to URL: " + url);
        System.out.println("Request Body: " + requestEntity.getBody());
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            long responseTime = System.currentTimeMillis() - startTime;
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Time: " + responseTime + " ms");

        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            System.err.println("Failed to send notification. Error: " + e.getMessage());
            System.err.println("Response Time: " + responseTime + " ms");
        }
    }
}
