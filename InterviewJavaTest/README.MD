# Webhook Notifier Interview Test Project

I built this using Spring, it is a bit overkill to use Spring for such a simple program,
however I thought since Spring is mentioned in the tech stack it would be best.

This project was build with Java 21 and Gradle v8.12.1.

## Instructions

### Build and Run
1. Build the project:
   ```bash
   ./gradlew build
   ```
2. Run the application:
   ```bash
   java -jar build/libs/InterviewJavaTest-1.0.0.jar <path-to-json-file>
   ```

### Testing
Tests are run as part of the gradle build, but can also be run with:
```bash
./gradlew test
```

### Example JSON File
```json
{
  "notificationUrl": "https://webhook.site/test-url",
  "notificationContent": {
    "reportUID": "20fb8e02-9c55-410a-93a9-489c6f1d7598",
    "studyInstanceUID": "9998e02-9c55-410a-93a9-489c6f789798"
  }
}
```
