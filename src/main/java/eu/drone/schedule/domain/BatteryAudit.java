package eu.drone.schedule.domain;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "battery_audit", schema = "public")
public final class BatteryAudit {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private UUID droneId;

    private String droneSerialNumber;

    private int batteryCapacity;

    @CreationTimestamp
    Instant createdAt;

}
