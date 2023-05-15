package eu.drone.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class DroneNotLoadingException extends RuntimeException {

    public DroneNotLoadingException() {
        super("Drone not loading. Incorrect ");
    }

}
