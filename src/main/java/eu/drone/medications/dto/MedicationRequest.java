package eu.drone.medications.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public record MedicationRequest(

        @NotBlank @Pattern(regexp = "^[\\w-]+$", message = "Name must allowed only letters, numbers, ‘-‘, ‘_’!")
        String name,

        @Positive
        int weightInGram,

        @NotBlank @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code must allowed only upper case, letters, underscore and numbers!")
        String code

) {

}
