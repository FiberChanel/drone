package eu.drone.drones.service;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.dto.DroneRequest;
import eu.drone.medications.domain.Medication;
import java.util.List;
import java.util.UUID;

public interface DroneService {

    Drone register(DroneRequest droneRequest);

    List<Medication> checkingLoadedMedicationsById(UUID id);

    void loadingWithMedicationItemsById(UUID id, List<UUID> medications);

    void completeLoading(UUID droneId);

    List<Drone> checkAvailableForLoading();

    int checkBatteryLevelById(UUID id);

    void delete(UUID id);

    void startDelivery(UUID id);

    void deliveryCompleted(UUID id);

    void returning(UUID id);

    void readyForNewDelivery(UUID id);

    Drone findRequiredById(UUID id);

    List<Drone> findAll();

    int getDronesCount();

}
