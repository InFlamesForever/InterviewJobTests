package org.example;

import org.example.domain.NotificationContent;
import org.example.domain.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class NotificationSenderTest {

    private final RestTemplate mockedRestTemplate = Mockito.mock(RestTemplate.class);

    @BeforeEach
    public void init() {
        when(mockedRestTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Received notification"));
    }

    @Test
    public void sendNotificationToCorrectURL() {
        final String url = "https://webhook.site/test-url";
        NotificationRequest payload = new NotificationRequest();
        payload.setNotificationUrl(url);

        NotificationContent content = new NotificationContent();
        content.setReportUID("dummy-report");
        content.setStudyInstanceUID("dummy-study");
        payload.setNotificationContent(content);

        NotificationSender sender = new NotificationSender(mockedRestTemplate);
        sender.sendNotification(payload);

        @SuppressWarnings("unchecked") // This is fine, you can't parameterise a .class, always annoyed me there's no way in java to do this without an unchecked warning
        ArgumentCaptor<HttpEntity<NotificationContent>> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(mockedRestTemplate, times(1)).postForEntity(eq(url), captor.capture(), eq(String.class));

        assertNotNull(captor.getValue());
        NotificationContent capturedContent = captor.getValue().getBody();
        assertNotNull(capturedContent);
        assertEquals("dummy-report", capturedContent.getReportUID());
        assertEquals("dummy-study", capturedContent.getStudyInstanceUID());
    }

}
