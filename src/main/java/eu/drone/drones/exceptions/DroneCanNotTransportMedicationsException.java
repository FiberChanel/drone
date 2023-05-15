package eu.drone.drones.exceptions;

public final class DroneCanNotTransportMedicationsException extends RuntimeException {

    public DroneCanNotTransportMedicationsException() {
        super("Drone can't delivery this cargo, incorrect state, battery level or cargo weight");
    }

}
