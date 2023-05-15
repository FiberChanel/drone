package eu.drone.delivery.rest;

import eu.drone.delivery.executor.DeliverySimulationExecutor;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/delivery-simulation")
public class DeliverySimulationController {

    private final DeliverySimulationExecutor deliverySimulationExecutor;

    public DeliverySimulationController(DeliverySimulationExecutor simulationExecutor) {
        this.deliverySimulationExecutor = simulationExecutor;
    }

    @Operation(operationId = "start-delivery-simulation", summary = "Start delivery simulation process")
    @GetMapping("/{droneId}/start")
    public void startSimulation(@PathVariable UUID droneId) {
        deliverySimulationExecutor.startSimulation(droneId);

    }

}
