package eu.drone.medications.service.impl;

import eu.drone.medications.domain.Medication;
import eu.drone.medications.dto.MedicationRequest;
import eu.drone.medications.repository.MedicationRepository;
import eu.drone.medications.service.MedicationService;
import java.io.IOException;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public void create(MedicationRequest medicationRequest, MultipartFile file) throws IOException {
        var medication = new Medication();
        medication.setName(medicationRequest.name());
        medication.setWeightInGram(medicationRequest.weightInGram());
        medication.setCode(medicationRequest.code());
        medication.setImage(file.getBytes());

        medicationRepository.save(medication);
    }

    @Override
    public void delete(UUID id) {
        medicationRepository.deleteById(id);
    }

}
