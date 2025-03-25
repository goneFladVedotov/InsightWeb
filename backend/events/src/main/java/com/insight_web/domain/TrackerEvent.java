package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClickTrackerEvent.class, name = "CLICK"),
        @JsonSubTypes.Type(value = CustomTrackerEvent.class, name = "CUSTOM"),
        @JsonSubTypes.Type(value = FormSubmitTrackerEvent.class, name = "FORM_SUBMIT"),
        @JsonSubTypes.Type(value = PageViewTrackerEvent.class, name = "PAGE_VIEW"),
        @JsonSubTypes.Type(value = ScrollTrackerEvent.class, name = "SCROLL"),
        @JsonSubTypes.Type(value = SessionEndTrackerEvent.class, name = "SESSION_END"),
        @JsonSubTypes.Type(value = SessionStartTrackerEvent.class, name = "SESSION_START"),
        @JsonSubTypes.Type(value = TimeOnPageTrackerEvent.class, name = "TIME_ON_PAGE"),
})
@Data
public class TrackerEvent {
    protected Instant timestamp;
    protected String siteId;
}

