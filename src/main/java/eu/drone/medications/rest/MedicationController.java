package eu.drone.medications.rest;

import eu.drone.medications.dto.MedicationRequest;
import eu.drone.medications.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/medications")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @Operation(operationId = "create", summary = "Create new medication")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestPart("medication") @Valid MedicationRequest medicationRequest, @RequestPart("file") @NotNull MultipartFile file)
            throws IOException {
        medicationService.create(medicationRequest, file);
    }

    @Operation(operationId = "delete", summary = "Delete exists medication")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        medicationService.delete(id);
    }


}
