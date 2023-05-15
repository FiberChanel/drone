package eu.drone.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class DroneBatteryCapacityException extends RuntimeException {

    public DroneBatteryCapacityException() {
        super("Can't start loading a drone with medication item with remain battery capacity less then 25%!");
    }

}
