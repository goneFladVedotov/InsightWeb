package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

import java.time.Duration;

@Data
public class TimeOnPageTrackerEvent extends TrackerEvent{
    private Duration timeSpent;
}
