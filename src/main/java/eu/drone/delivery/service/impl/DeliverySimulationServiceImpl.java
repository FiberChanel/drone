package eu.drone.delivery.service.impl;

import eu.drone.delivery.service.DeliverySimulationService;
import eu.drone.drones.domain.Drone.Model;
import eu.drone.drones.service.DroneService;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class DeliverySimulationServiceImpl implements DeliverySimulationService {

    private final DroneService droneService;

    public DeliverySimulationServiceImpl(DroneService droneService) {
        this.droneService = droneService;
    }

    public void startSimulation(UUID droneId) {
        var drone = droneService.findRequiredById(droneId);
        droneService.startDelivery(droneId);

        var durationSeconds = calculateDeliveryAndReturnTimeByModel(drone.getModel());
        sleep(durationSeconds);
        droneService.deliveryCompleted(droneId);

        sleep(durationSeconds);
        droneService.returning(droneId);

        sleep(5);
        droneService.readyForNewDelivery(droneId);
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private int calculateDeliveryAndReturnTimeByModel(Model model) {
        return switch (model) {
            case LIGHTWEIGHT -> 5;
            case MIDDLEWEIGHT -> 10;
            case CRUISERWEIGHT -> 15;
            case HEAVYWEIGHT -> 20;
        };
    }

}
