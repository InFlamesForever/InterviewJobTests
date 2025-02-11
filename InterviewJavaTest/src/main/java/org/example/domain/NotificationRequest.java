package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

/**
 * POJO class to represent the JSON content of the file.
 */
public class NotificationRequest {
    @JsonProperty("notificationUrl")
    private String notificationUrl;

    @JsonProperty("notificationContent")
    private NotificationContent notificationContent;

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(@NonNull final String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public NotificationContent getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(@NonNull final NotificationContent notificationContent) {
        this.notificationContent = notificationContent;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "notificationUrl='" + notificationUrl + '\'' +
                ", notificationContent=" + notificationContent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof NotificationRequest that)) return false;

        return new EqualsBuilder()
                .append(
                        getNotificationUrl(),
                        that.getNotificationUrl()
                )
                .append(
                        getNotificationContent(),
                        that.getNotificationContent()
                )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getNotificationUrl())
                .append(getNotificationContent())
                .toHashCode();
    }
}
