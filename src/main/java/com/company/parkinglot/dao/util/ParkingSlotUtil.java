package com.company.parkinglot.dao.util;

import com.company.parkinglot.contract.ParkingLot;
import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.contract.ParkingSlotStatus;
import com.company.parkinglot.contract.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParkingSlotUtil {

    public static ParkingSlot getNearestAvailableParkingSlot(Set<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().filter(x -> x.getParkingSlotStatus().equals(ParkingSlotStatus.AVAILABLE)).findFirst().orElse(null);
    }

    public static void bookSlot(ParkingLot parkingLot, ParkingSlot slot, Vehicle vehicle) {
        slot.setParkingSlotStatus(ParkingSlotStatus.OCCUPIED);
        parkingLot.getParkingSlots().put(slot, vehicle);
    }

    public static boolean isVehicleAlreadyParked (Collection<Vehicle> parkedVehicles, String registrationNum) {
        return parkedVehicles.stream().filter(x -> (x != null && x.getRegistrationNum().equalsIgnoreCase(registrationNum))).findAny().orElse(null) != null;
    }

    public static void setSlotAvailable (Map<ParkingSlot, Vehicle> parkingSlots, int slotNumber) {
        for (ParkingSlot slot: parkingSlots.keySet()) {
            if (slot.getSlotNumber() == slotNumber) {
                slot.setParkingSlotStatus(ParkingSlotStatus.AVAILABLE);
                parkingSlots.put(slot, null);
            }
        }
    }
}
