package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonTypeName("CLICK")
@Data
public class ClickTrackerEvent extends TrackerEvent {
    private String element;
}
