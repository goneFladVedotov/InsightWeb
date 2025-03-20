package com.insight_web.data_ingestion_server.domain;

import lombok.Data;

import java.time.Duration;

@Data
public class SessionEndTrackerEvent extends TrackerEvent{
    private String sessionEnd;
    private Duration duration;
}
