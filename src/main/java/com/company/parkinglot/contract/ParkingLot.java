package com.company.parkinglot.contract;

import java.util.*;

public class ParkingLot {

    private static Map<ParkingSlot, Vehicle> parkingSlots;
    private static ParkingLot instance;

    public static ParkingLot getInstance(int capacity) {
        if (instance == null) {
            instance = new ParkingLot(capacity);
        }
        return instance;
    }

    private ParkingLot (int capacity) {
        parkingSlots = new TreeMap(new HashMap<ParkingLot, Vehicle>(capacity));
        for (int i=0; i<capacity; i++) {
            parkingSlots.put(new ParkingSlot(i+1, ParkingSlotStatus.AVAILABLE), null);
        }
    }

    public Map<ParkingSlot, Vehicle> getParkingSlots() {
        return parkingSlots;
    }

    public void clear() {
        parkingSlots = null;
        instance = null;
    }
}
