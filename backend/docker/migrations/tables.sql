CREATE TABLE events
(
    event_date Date,
    event_time DateTime,
    event_type String,
    session_id String,
    url String,
    referrer String,
    ip IPv4,
    device_type Enum('MOBILE' = 1, 'DESKTOP' = 2, 'TABLET' = 3),
    browser String,
    os String,
    language String,

    -- Специфичные поля событий
    element_id Nullable(String),
    element_text Nullable(String),
    conversion_type Nullable(String),
    conversion_value Nullable(Float64),
    error_type Nullable(String),
    error_message Nullable(String),
    page_title Nullable(String),
    screen_width Nullable(UInt16),
    scroll_depth Nullable(UInt8),
    search_query Nullable(String),
    search_results_count Nullable(UInt32),
    session_duration_ms Nullable(UInt64)
)
    ENGINE = MergeTree()
        PARTITION BY toYYYYMM(event_date)
        ORDER BY (url, event_date, event_type)
        SETTINGS index_granularity = 8192;