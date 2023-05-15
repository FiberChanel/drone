package eu.drone.drones.rest;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.dto.DroneRequest;
import eu.drone.drones.service.DroneService;
import eu.drone.medications.domain.Medication;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @Operation(operationId = "register", summary = "Register new drone")
    @PostMapping(value = "/register")
    public void register(@RequestBody @Valid DroneRequest droneRequest) {
        droneService.register(droneRequest);
    }

    @Operation(operationId = "loading-with-medications", summary = "Loading drone with medication")
    @PostMapping("/{id}/loading-with-medications")
    public void loading(@PathVariable UUID id, @RequestBody @Valid List<UUID> medicationIds) {
        droneService.loadingWithMedicationItemsById(id, medicationIds);
    }

    @Operation(operationId = "check-loaded-medications", summary = "Get loaded medication for drone")
    @GetMapping("/{id}/check-loaded-medications")
    public List<Medication> getLoadedMedicationsByDroneId(@PathVariable UUID id) {
        return droneService.checkingLoadedMedicationsById(id);
    }

    @Operation(operationId = "check-available", summary = "Get free drones (with idle state)")
    @GetMapping("/check-available")
    public List<Drone> checkAvailableForLoading() {
        return droneService.checkAvailableForLoading();
    }

    @Operation(operationId = "check-battery-level", summary = "Check drone battery level")
    @GetMapping("/{id}/check-battery-level")
    public int checkBatteryLevelById(@PathVariable UUID id) {
        return droneService.checkBatteryLevelById(id);
    }

    @Operation(operationId = "delete", summary = "Delete exists drone")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        droneService.delete(id);
    }

}
