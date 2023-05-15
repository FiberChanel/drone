package eu.drone.schedule.service.impl;

import eu.drone.schedule.domain.BatteryAudit;
import eu.drone.schedule.repository.BatteryAuditRepository;
import eu.drone.schedule.service.BatteryAuditService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BatteryAuditServiceImpl implements BatteryAuditService {

    private final BatteryAuditRepository batteryAuditRepository;

    public BatteryAuditServiceImpl(BatteryAuditRepository batteryAuditRepository) {
        this.batteryAuditRepository = batteryAuditRepository;
    }

    @Override
    public void saveAuditLogs(List<BatteryAudit> batteryAudits) {
        batteryAuditRepository.saveAll(batteryAudits);
    }

}
