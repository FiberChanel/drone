package eu.drone.schedule.service;

import eu.drone.schedule.domain.BatteryAudit;
import java.util.List;

public interface BatteryAuditService {

    void saveAuditLogs(List<BatteryAudit> batteryAudits);

}
