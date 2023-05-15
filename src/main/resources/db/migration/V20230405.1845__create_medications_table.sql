CREATE TABLE IF NOT EXISTS MEDICATIONS
(
    ID         UUID PRIMARY KEY,
    NAME       TEXT                                      NOT NULL,
    WEIGHT     DECIMAL                                   NOT NULL,
    CODE       TEXT                                      NOT NULL,
    IMAGE      BYTEA                                     NOT NULL,
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL
);
