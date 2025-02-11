package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.NotificationRequest;

import java.io.File;
import java.io.IOException;


/**
 * Utility class for reading JSON files and converting them into NotificationRequest objects.
 */
public class JsonFileUtil {
    public static NotificationRequest readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), NotificationRequest.class);
    }
}