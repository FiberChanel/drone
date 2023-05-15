CREATE TABLE IF NOT EXISTS DRONES
(
    ID               UUID PRIMARY KEY,
    SERIAL_NUMBER    TEXT                                      NOT NULL,
    MODEL            TEXT                                      NOT NULL,
    WEIGHT_LIMIT     DECIMAL                                   NOT NULL,
    BATTERY_CAPACITY INTEGER                                   NOT NULL,
    STATE            TEXT                                      NOT NULL,
    CREATED_AT       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL
);
