package eu.drone.drones.domain;

import eu.drone.drones.exceptions.DroneCanNotTransportMedicationsException;
import eu.drone.drones.exceptions.DroneMedicationsException;
import eu.drone.drones.exceptions.DroneNotLoadingException;
import eu.drone.medications.domain.Medication;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "drones", schema = "public")
public final class Drone {

    private static final int LOW_CHARGE_PERCENT = 25;
    private static final int MAX_CHARGE_PERCENT = 100;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "weight_limit")
    private int weightLimitInGram;

    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Medication> medications;

    public void loading(List<Medication> medications) {
        if (medications == null || medications.isEmpty()) {
            throw new DroneMedicationsException();
        }

        var totalMedicationsWeight = medications.stream().mapToInt(Medication::getWeightInGram).sum();

        if (!isAllowedForLoading() || totalMedicationsWeight > weightLimitInGram) {
            throw new DroneCanNotTransportMedicationsException();
        }
        this.state = State.LOADING;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        this.medications = medications;
        this.state = State.LOADED;
    }

    public enum Model {
        LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, HEAVYWEIGHT
    }

    public enum State {
        IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING
    }

    public static Drone idle() {
        var drone = new Drone();
        drone.setState(State.IDLE);
        return drone;
    }

    public boolean isAllowedForLoading() {
        return isIdle() && batteryCapacity >= LOW_CHARGE_PERCENT;
    }

    public void loadingComplete() {
        if (!isLoading()) {
            throw new DroneNotLoadingException();
        }
        this.state = State.LOADED;
    }

    public boolean isReadyForDelivery() {
        return isLoaded() && batteryCapacity >= LOW_CHARGE_PERCENT;
    }

    public void startDelivery() {
        this.state = State.DELIVERING;
    }

    private boolean isIdle() {
        return State.IDLE == state;
    }

    private boolean isLoaded() {
        return State.LOADED == state;
    }

    public void spendEnergyForDelivery() {
        this.batteryCapacity = batteryCapacity - 10;
    }

    public void spendEnergyForReturning() {
        this.batteryCapacity = batteryCapacity - 10;
    }

    private boolean isLoading() {
        return State.LOADING == state;
    }


}
