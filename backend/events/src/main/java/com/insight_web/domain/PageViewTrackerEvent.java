package com.insight_web.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("PAGE_VIEW")
public class PageViewTrackerEvent extends TrackerEvent {
    private String title;
    private String referrer;
}
