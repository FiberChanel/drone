package eu.drone.delivery.executor;

import eu.drone.delivery.service.DeliverySimulationService;
import java.util.UUID;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class DeliverySimulationExecutor {

    private final TaskExecutor taskExecutor;
    private final DeliverySimulationService deliverySimulationService;

    public DeliverySimulationExecutor(TaskExecutor taskExecutor, DeliverySimulationService deliverySimulationService) {
        this.taskExecutor = taskExecutor;
        this.deliverySimulationService = deliverySimulationService;
    }

    private class DeliverySimulationTask implements Runnable {

        private final UUID droneId;

        public DeliverySimulationTask(UUID droneId) {
            this.droneId = droneId;
        }

        @Override
        public void run() {
            deliverySimulationService.startSimulation(droneId);
        }

    }

    public void startSimulation(UUID droneId) {
        taskExecutor.execute(new DeliverySimulationTask(droneId));
    }

}
