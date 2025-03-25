package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("SCROLL")
public class ScrollTrackerEvent extends TrackerEvent {
    private Integer scrollPercent;
}
