package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("TIME_ON_PAGE")
public class TimeOnPageTrackerEvent extends TrackerEvent {
    private Duration timeSpent;
}
