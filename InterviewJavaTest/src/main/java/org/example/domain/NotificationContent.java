package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.NonNull;

/**
 * POJO class to represent the JSON content of the request.
 */
public class NotificationContent {
    @JsonProperty("reportUID")
    private String reportUID;
    @JsonProperty("studyInstanceUID")
    private String studyInstanceUID;

    public String getReportUID() {
        return reportUID;
    }

    public void setReportUID(@NonNull final String reportUID) {
        this.reportUID = reportUID;
    }

    public String getStudyInstanceUID() {
        return studyInstanceUID;
    }

    public void setStudyInstanceUID(@NonNull final String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
    }


    @Override
    public String toString() {
        return "NotificationContent{" +
                "reportUID='" + reportUID + '\'' +
                ", studyInstanceUID='" + studyInstanceUID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof NotificationContent that)) return false;

        return new EqualsBuilder()
                .append(
                        getReportUID(),
                        that.getReportUID()
                )
                .append(
                        getStudyInstanceUID(),
                        that.getStudyInstanceUID()
                )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getReportUID())
                .append(getStudyInstanceUID())
                .toHashCode();
    }
}
