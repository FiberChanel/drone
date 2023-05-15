package eu.drone.medications.service;

import eu.drone.medications.dto.MedicationRequest;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MedicationService {

    void create(MedicationRequest medicationRequest, MultipartFile file) throws IOException;

    void delete(UUID id);

}
