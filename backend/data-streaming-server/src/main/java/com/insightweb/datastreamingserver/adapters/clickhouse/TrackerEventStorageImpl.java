package com.insightweb.datastreamingserver.adapters.clickhouse;

import com.insight_web.domain.*;
import com.insightweb.datastreamingserver.usecase.TrackerEventStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackerEventStorageImpl implements TrackerEventStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void save(TrackerEvent event) {
        if (event instanceof ClickTrackerEvent) {
            saveClick((ClickTrackerEvent) event);
        } else if (event instanceof CustomTrackerEvent) {
            saveCustom((CustomTrackerEvent) event);
        } else if (event instanceof FormSubmitTrackerEvent) {
            saveFormSubmit((FormSubmitTrackerEvent) event);
        } else if (event instanceof PageViewTrackerEvent) {
            savePageView((PageViewTrackerEvent) event);
        } else if (event instanceof ScrollTrackerEvent) {
            saveScroll((ScrollTrackerEvent) event);
        } else if (event instanceof SessionStartTrackerEvent) {
            saveSessionStart((SessionStartTrackerEvent) event);
        } else if (event instanceof SessionEndTrackerEvent) {
            saveSessionEnd((SessionEndTrackerEvent) event);
        } else if (event instanceof TimeOnPageTrackerEvent) {
            saveTimeOnPage((TimeOnPageTrackerEvent) event);
        } else {
            throw new RuntimeException("Unknown event");
        }
    }

    private void saveTimeOnPage(TimeOnPageTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO time_on_page_event_data (site_id, timestamp, time_spent) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getTimeSpent());
    }

    private void saveSessionEnd(SessionEndTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO session_end_event_data (site_id, timestamp, session_end, duration) VALUES (?, ?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getSessionEnd(), event.getDuration());
    }

    private void saveSessionStart(SessionStartTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO session_start_event_data (site_id, timestamp, session_id) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getSessionId());
    }


    private void saveScroll(ScrollTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO scroll_event_data (site_id, timestamp, scroll_percent) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getScrollPercent());
    }

    private void savePageView(PageViewTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO page_view_event_data (site_id, timestamp, title, referrer) VALUES (?, ?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getTitle(), event.getReferrer());
    }

    private void saveFormSubmit(FormSubmitTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO form_submit_event_data (site_id, timestamp, formId) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getFormId());
    }

    private void saveCustom(CustomTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO custom_event_data (site_id, timestamp, custom_events) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getCustomEvents());
    }

    private void saveClick(ClickTrackerEvent event) {
        jdbcTemplate.update("INSERT INTO click_event_data (site_id, timestamp, element) VALUES (?, ?, ?)", event.getSiteId(), event.getTimestamp(), event.getElement()); 
    }
}
