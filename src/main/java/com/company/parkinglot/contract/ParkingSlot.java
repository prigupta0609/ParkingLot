package com.company.parkinglot.contract;

public class ParkingSlot implements Comparable {

    private int slotNumber;
    private ParkingSlotStatus parkingSlotStatus;

    public ParkingSlot(int slotNumber, ParkingSlotStatus parkingSlotStatus) {
        this.slotNumber = slotNumber;
        this.parkingSlotStatus = parkingSlotStatus;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public ParkingSlotStatus getParkingSlotStatus() {
        return parkingSlotStatus;
    }

    public void setParkingSlotStatus(ParkingSlotStatus parkingSlotStatus) {
        this.parkingSlotStatus = parkingSlotStatus;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ParkingSlot) {
            if (this.slotNumber < ((ParkingSlot) o).slotNumber) {
                return -1;
            } else if (this.slotNumber > ((ParkingSlot) o).slotNumber) {
                return 1;
            }
        }
        return 0;
    }
}
