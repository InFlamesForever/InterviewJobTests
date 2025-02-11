package org.example;

import org.example.domain.NotificationRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonFileUtilTest {
    @Test
    public void fileReadCorrectly() throws IOException {
        NotificationRequest file = JsonFileUtil.readJsonFile("src/test/resources/testNotificationUrlAndContent.json");
        assertNotNull(file);
        assertEquals("https://webhook.site/sample-url", file.getNotificationUrl());
        assertEquals("20fb8e02-9c55-410a-93a9-489c6f1d7598", file.getNotificationContent().getReportUID());
        assertEquals("9998e02-9c55-410a-93a9-489c6f789798", file.getNotificationContent().getStudyInstanceUID());
    }
}
