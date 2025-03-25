package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("SESSION_END")
public class SessionEndTrackerEvent extends TrackerEvent {
    private String sessionEnd;
    private Duration duration;
}
