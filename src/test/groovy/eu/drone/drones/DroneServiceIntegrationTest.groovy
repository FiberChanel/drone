package eu.drone.drones

import eu.drone.drones.domain.Drone
import eu.drone.drones.domain.Drone.Model
import eu.drone.drones.domain.Drone.State
import eu.drone.drones.repository.DroneRepository
import eu.drone.drones.service.DroneService
import eu.drone.medications.domain.Medication
import eu.drone.medications.repository.MedicationRepository
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired

import javax.transaction.Transactional

final class DroneServiceIntegrationTest extends IntegrationTest {

    private static final String ACC_FILE_PATH = "src/test/resources/acc.jpg";
    private static final String GAVISCON_FILE_PATH = "src/test/resources/gaviscon.jpg";
    private static final String ORAJEL_FILE_PATH = "src/test/resources/orajel.jpg";

    @Autowired
    private DroneService sut

    @Autowired
    private DroneRepository droneRepository

    @Autowired
    private MedicationRepository medicationRepository

    private def accMedication;
    private def gavisconMedication;
    private def orajelMedication;

    def setup() {
        createMedicationsAndSave()
    }

    def cleanup() {
        droneRepository.deleteAll()
    }

    @Transactional
    def 'must successfully return all free drones'() {
        given:
        def idleDrone1 = createDroneAndSave("L01", Model.LIGHTWEIGHT, 50, 100, State.IDLE)
        def idleDrone2 = createDroneAndSave("M01", Model.MIDDLEWEIGHT, 100, 100, State.IDLE)
        def returningDrone = createDroneAndSave("C01", Model.CRUISERWEIGHT, 250, 100, State.RETURNING)

        when:
        def availableForLoading = sut.checkAvailableForLoading()

        then:

        Assert.assertEquals(2, availableForLoading.size())
        availableForLoading ==~ [idleDrone1, idleDrone2]

        availableForLoading.forEach { d ->
            nullEmptyCollection(d.medications)

        }

    }

    @Transactional
    def 'must successfully return medication when drone loaded'() {
        given:
        def idleDrone1 = createDroneAndSave("L01", Model.LIGHTWEIGHT, 50, 100, State.IDLE)
        sut.loadingWithMedicationItemsById(idleDrone1.id, List.of(accMedication.id, gavisconMedication.id))

        when:
        def loadedMedications = sut.checkingLoadedMedicationsById(idleDrone1.id)

        then:
        loadedMedications ==~ [accMedication, gavisconMedication]
    }

    private def createDroneAndSave(String serialNumber, Model model, int weightLimit, int batteryCapacity, State state) {
        droneRepository.save(createDrone(serialNumber, model, weightLimit, batteryCapacity, state))
    }


    def 'must return battery capacity by drone id'() {
        given:
        def drone = createIdleDrone("L01", Model.LIGHTWEIGHT, 50, 100)
        droneRepository.save(drone)

        when:
        def expectedCapacity = sut.checkBatteryLevelById(drone.id)

        then:
        drone.batteryCapacity == expectedCapacity

    }

    private static def createIdleDrone(String serialNumber, Model model, int weightLimit, int batteryCapacity) {
        def drone = new Drone()
        drone.serialNumber = serialNumber
        drone.model = model
        drone.weightLimitInGram = weightLimit
        drone.batteryCapacity = batteryCapacity
        drone.state = State.IDLE
        return drone
    }

    private static def createDrone(String serialNumber, Model model, int weightLimit, int batteryCapacity, State state) {
        def drone = new Drone()
        drone.serialNumber = serialNumber
        drone.model = model
        drone.weightLimitInGram = weightLimit
        drone.batteryCapacity = batteryCapacity
        drone.state = state
        return drone
    }

    private static def nullEmptyCollection(Collection<?> items) {
        return items == null
    }

    private def createMedicationsAndSave() {
        accMedication = medicationRepository.save(createMedication(10, "ccc", "ACC", File.createTempFile(new File(ACC_FILE_PATH).getName(), "jpg").getBytes()))
        gavisconMedication = medicationRepository.save(createMedication(20, "gaviscon", "GVC", File.createTempFile(new File(GAVISCON_FILE_PATH).getName(), "jpg").getBytes()))
        orajelMedication = medicationRepository.save(createMedication(80, "orajel", "ORJ", File.createTempFile(new File(ORAJEL_FILE_PATH).getName(), "jpg").getBytes()))
    }

    private def createMedication(int weightInGram, String name, String code, byte[] image) {
        def medication = new Medication()
        medication.setWeightInGram(weightInGram)
        medication.setName(name)
        medication.setCode(code)
        medication.setImage(image)
        return medication

    }
}
