package com.insightweb.datastreamingserver.usecase;

import com.insight_web.domain.TrackerEvent;

public interface TrackerEventStorage {
    void save(TrackerEvent event);
}
