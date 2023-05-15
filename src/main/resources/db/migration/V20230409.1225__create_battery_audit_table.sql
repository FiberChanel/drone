CREATE TABLE IF NOT EXISTS BATTERY_AUDIT
(
    ID                  UUID PRIMARY KEY,
    DRONE_ID            TEXT                                      NOT NULL,
    DRONE_SERIAL_NUMBER TEXT                                      NOT NULL,
    BATTERY_CAPACITY    INTEGER                                   NOT NULL,
    CREATED_AT          TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL
);
