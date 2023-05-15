package eu.drone.drones.repository;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.domain.Drone.State;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DroneRepository extends JpaRepository<Drone, UUID> {

    List<Drone> findByState(State state);

    @Query(nativeQuery = true, value = "select battery_capacity from drones where id =:id")
    int checkBatteryLevelById(@Param("id") UUID id);

    List<Drone> findDronesByStateIs(State state);

    Optional<Drone> findDroneByIdAndState(UUID id, State state);



}
