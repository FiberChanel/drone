package eu.drone.schedule.task;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.service.DroneService;
import eu.drone.schedule.domain.BatteryAudit;
import eu.drone.schedule.service.BatteryAuditService;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatteryAuditScheduledTask {

    private final DroneService droneService;
    private final BatteryAuditService batteryAuditService;

    public BatteryAuditScheduledTask(DroneService droneService, BatteryAuditService batteryAuditService) {
        this.droneService = droneService;
        this.batteryAuditService = batteryAuditService;
    }

    @Scheduled(fixedDelayString = "${schedule.battery_audit.periodInSecond}")
    public void reportDroneBatteryCapacity() {
        if (droneService.getDronesCount() > 0) {
            var batteryAudits = droneService.findAll().stream().map(this::mapToBatteryAudit).collect(Collectors.toList());
            batteryAuditService.saveAuditLogs(batteryAudits);
        }
    }

    private BatteryAudit mapToBatteryAudit(Drone drone) {
        var batteryAuditRecord = new BatteryAudit();
        batteryAuditRecord.setDroneId(drone.getId());
        batteryAuditRecord.setDroneSerialNumber(drone.getSerialNumber());
        batteryAuditRecord.setBatteryCapacity(drone.getBatteryCapacity());
        return batteryAuditRecord;
    }

}
