package com.company.parkinglot.dao.util;

import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.contract.ParkingSlotStatus;
import com.company.parkinglot.contract.Vehicle;

import java.util.List;

public class ParkingSlotUtil {

    public static ParkingSlot getNearestAvailableParkingSlot(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().filter(x -> x.getParkingSlotStatus().equals(ParkingSlotStatus.AVAILABLE)).findFirst().orElse(null);
    }

    public static void bookSlot(ParkingSlot slot, Vehicle vehicle) {
        slot.setVehicle(vehicle);
        slot.setParkingSlotStatus(ParkingSlotStatus.OCCUPIED);
    }

    public static boolean isVehicleAlreadyParked (List<ParkingSlot> parkingSlots, String registrationNum) {
        return parkingSlots.stream().filter(x -> (x.getVehicle() != null && x.getVehicle().getRegistrationNum().equalsIgnoreCase(registrationNum))).findAny().orElse(null) != null;
    }

    public static void setSlotAvailable (List<ParkingSlot> parkingSlots, int slotNumber) {
        ParkingSlot slot = parkingSlots.get(slotNumber-1);
        slot.setVehicle(null);
        slot.setParkingSlotStatus(ParkingSlotStatus.AVAILABLE);
    }
}
