package eu.drone.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public final class DroneMaxWeightLimitException extends RuntimeException {

    public DroneMaxWeightLimitException(int calculatedWeight) {
        super("Drone max weight limit is 500 grams! Current weight = " + calculatedWeight + " grams!");
    }

}
