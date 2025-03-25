package com.insight_web.domain;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("CUSTOM")
public class CustomTrackerEvent extends TrackerEvent {
    private Map<String, String> customEvents;
}
