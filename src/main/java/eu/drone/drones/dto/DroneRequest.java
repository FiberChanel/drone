package eu.drone.drones.dto;

import eu.drone.drones.domain.Drone.Model;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public record DroneRequest(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Serial must allowed only letters, numbers, ‘-‘, ‘_’!")
        @Size(max = 100, message = "Serial number size length must be <= 100!")
        String serialNumber,

        @NotNull
        Model model,

        @Positive
        @Max(value = 500, message = "Weight limit in gram should not be greater than 500")
        int weightLimitInGram,

        @PositiveOrZero
        @Max(value = 100)
        int batteryCapacity

) {

}
