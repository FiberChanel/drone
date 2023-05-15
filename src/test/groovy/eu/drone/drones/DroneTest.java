package eu.drone.drones;

import eu.drone.drones.domain.Drone;
import eu.drone.drones.domain.Drone.Model;
import eu.drone.drones.domain.Drone.State;
import eu.drone.drones.exceptions.DroneCanNotTransportMedicationsException;
import eu.drone.drones.exceptions.DroneMedicationsException;
import eu.drone.drones.exceptions.DroneNotLoadingException;
import eu.drone.medications.domain.Medication;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DroneTest {

    @ParameterizedTest
    @MethodSource("lowBatteryChargeValues")
    public void canNotLoadDroneWhenLowBatteryCharge(int batteryLevel) {

        var sut = new Drone();
        sut.setBatteryCapacity(batteryLevel);

        var medications = createMedications();

        Assertions.assertThrows(
                DroneCanNotTransportMedicationsException.class,
                () -> sut.loading(medications)
        );
    }

    @ParameterizedTest
    @MethodSource("allNotIdleStateValues")
    public void canNotLoadDroneWhenItIsNotIdle(State state) {
        var sut = new Drone();
        sut.setState(state);

        var medications = createMedications();

        Assertions.assertThrows(
                DroneCanNotTransportMedicationsException.class,
                () -> sut.loading(medications)
        );
    }

    @Test
    public void canNotLoadDroneWhenCargoWeightGreaterThanDroneCapacity() {
        var sut = new Drone();
        sut.setWeightLimitInGram(50);

        var medications = createMedications();

        Assertions.assertThrows(
                DroneCanNotTransportMedicationsException.class,
                () -> sut.loading(medications)
        );
    }

    @Test
    public void canNotLoadingCompleteWhenDroneIsNotLoading() {
        var sut = new Drone();

        Assertions.assertThrows(
                DroneNotLoadingException.class,
                sut::loadingComplete
        );
    }

    @Test
    public void canNotLoadDroneWithNullCargo() {
        var sut = new Drone();

        CustomAssertions.assertExceptionThrownWithMessage(
                DroneMedicationsException.class,
                () -> sut.loading(null),
                "Medications must no be null or empty!"
        );
    }

    @Test
    public void successLoadedDrone() {
        var sut = createIdleDroneWithFullBattery();
        sut.loading(createMedications());

        Assertions.assertEquals(Drone.State.LOADED, sut.getState());
    }

    private Drone createIdleDroneWithFullBattery() {
        var drone = new Drone();
        drone.setSerialNumber("CRF-104500");
        drone.setModel(Model.CRUISERWEIGHT);
        drone.setState(State.IDLE);
        drone.setWeightLimitInGram(500);
        drone.setBatteryCapacity(100);
        return drone;
    }

    private List<Medication> createMedications() {
        var accLong = new Medication();
        accLong.setId(UUID.randomUUID());
        accLong.setName("AccLong");
        accLong.setCode("ACC");
        accLong.setWeightInGram(10);
        accLong.setImage("accLong".getBytes());

        var gaviscon = new Medication();
        gaviscon.setId(UUID.randomUUID());
        gaviscon.setName("gaviscon");
        gaviscon.setCode("GVC");
        gaviscon.setWeightInGram(100);
        gaviscon.setImage("gaviscon".getBytes());

        return List.of(accLong, gaviscon);
    }

    private static Stream<Arguments> lowBatteryChargeValues() {
        return IntStream.rangeClosed(0, 24).mapToObj(Arguments::of);
    }

    private static Stream<Arguments> allNotIdleStateValues() {
        return Arrays.stream(Drone.State.values()).filter(state -> state != Drone.State.IDLE).map(Arguments::of);
    }


}
