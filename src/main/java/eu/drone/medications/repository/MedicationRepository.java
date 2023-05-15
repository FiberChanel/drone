package eu.drone.medications.repository;

import eu.drone.medications.domain.Medication;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {

}
