CREATE TABLE IF NOT EXISTS DRONES_MEDICATIONS
(
    DRONE_ID       UUID NOT NULL REFERENCES DRONES (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    MEDICATIONS_ID UUID NOT NULL REFERENCES MEDICATIONS (ID) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (DRONE_ID, MEDICATIONS_ID)
)