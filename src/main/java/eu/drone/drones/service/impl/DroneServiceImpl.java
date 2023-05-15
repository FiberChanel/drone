package eu.drone.drones.service.impl;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.domain.Drone.State;
import eu.drone.drones.dto.DroneRequest;
import eu.drone.drones.exceptions.DroneCanNotTransportMedicationsException;
import eu.drone.drones.exceptions.DroneCountLimitException;
import eu.drone.drones.exceptions.DroneNotFoundException;
import eu.drone.drones.repository.DroneRepository;
import eu.drone.drones.service.DroneService;
import eu.drone.medications.domain.Medication;
import eu.drone.medications.repository.MedicationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DroneServiceImpl implements DroneService {

    private final static int MAX_DRONE_COUNT = 10;

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Drone register(DroneRequest droneRequest) {
        if (droneRepository.count() >= MAX_DRONE_COUNT) {
            throw new DroneCountLimitException();
        }
        var drone = eu.drone.drones.domain.Drone.idle();
        drone.setSerialNumber(droneRequest.serialNumber());
        drone.setModel(droneRequest.model());
        drone.setWeightLimitInGram(droneRequest.weightLimitInGram());
        drone.setBatteryCapacity(droneRequest.batteryCapacity());
        return droneRepository.save(drone);

    }

    @Override
    public void loadingWithMedicationItemsById(UUID id, List<UUID> medicationIds) {
        var medications = medicationRepository.findAllById(medicationIds);
        var drone = droneRepository.findDroneByIdAndState(id, State.IDLE).orElseThrow(DroneNotFoundException::new);

        drone.loading(medications);
        droneRepository.save(drone);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Medication> checkingLoadedMedicationsById(UUID id) {
        return findRequiredById(id).getMedications();
    }

    @Transactional(readOnly = true)
    @Override
    public int checkBatteryLevelById(UUID id) {
        return droneRepository.checkBatteryLevelById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Drone> checkAvailableForLoading() {
        return droneRepository.findDronesByStateIs(State.IDLE);
    }

    public void completeLoading(UUID id) {
        var drone = findRequiredById(id);
        drone.loadingComplete();
        droneRepository.save(drone);
    }

    @Override
    public void startDelivery(UUID id) {
        var drone = findRequiredById(id);
        if (!drone.isReadyForDelivery()) {
            throw new DroneCanNotTransportMedicationsException();
        }
        drone.startDelivery();
        droneRepository.save(drone);

    }

    @Override
    public void deliveryCompleted(UUID id) {
        var drone = findRequiredById(id);
        drone.spendEnergyForDelivery();
        drone.setState(State.DELIVERED);
        drone.getMedications().clear();
        droneRepository.save(drone);
    }

    @Override
    public void returning(UUID id) {
        var drone = findRequiredById(id);
        drone.spendEnergyForReturning();
        drone.setState(State.RETURNING);
        droneRepository.save(drone);
    }

    @Override
    public void readyForNewDelivery(UUID id) {
        var drone = findRequiredById(id);
        drone.setState(State.IDLE);
        droneRepository.save(drone);
    }


    @Transactional(readOnly = true)
    public Drone findRequiredById(UUID id) {
        return droneRepository.findById(id).orElseThrow(DroneNotFoundException::new);
    }

    @Override
    public void delete(UUID id) {
        droneRepository.deleteById(id);
    }


    @Override
    public List<Drone> findAll() {
        return droneRepository.findAll();
    }

    @Override
    public int getDronesCount() {
        return (int) droneRepository.count();
    }

}
