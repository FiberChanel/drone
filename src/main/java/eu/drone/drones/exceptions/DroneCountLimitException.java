package eu.drone.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public final class DroneCountLimitException extends RuntimeException {

    public DroneCountLimitException() {
        super("Already registered max count of drones = 10");
    }

}
