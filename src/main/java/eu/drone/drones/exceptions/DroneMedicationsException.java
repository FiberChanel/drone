package eu.drone.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public final class DroneMedicationsException extends RuntimeException {

    public DroneMedicationsException() {
        super("Medications must no be null or empty!");
    }

}
