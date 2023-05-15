package eu.drone.schedule.repository;

import eu.drone.schedule.domain.BatteryAudit;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryAuditRepository extends JpaRepository<BatteryAudit, UUID> {

}
