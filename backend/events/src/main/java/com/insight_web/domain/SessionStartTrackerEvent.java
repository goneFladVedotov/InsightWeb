package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("SESSION_START")
public class SessionStartTrackerEvent extends TrackerEvent {
    private String sessionId;
}
