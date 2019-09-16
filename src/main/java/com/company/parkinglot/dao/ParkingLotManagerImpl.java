package com.company.parkinglot.dao;

import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.contract.ParkingSlotStatus;
import com.company.parkinglot.contract.Vehicle;
import com.company.parkinglot.dao.util.ParkingSlotUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingLotManagerImpl implements ParkingLotManager {

    private static ParkingLotManagerImpl instance = null;
    private static List<ParkingSlot> parkingSlots = null;
    private static final int NOT_AVAILABLE = -1;
    private static final int VEHICLE_ALREADY_PARKED = -2;
    private static final int SUCCESS = 1;
    private static final int INVALID_SLOT = -3;

    public static ParkingLotManagerImpl getInstance(int capacity) {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new ParkingLotManagerImpl(capacity);
                }
            }
        }
        return instance;
    }

    private ParkingLotManagerImpl (int capacity) {
        parkingSlots = new ArrayList<ParkingSlot>(capacity);
        for (int i=0; i<capacity; i++) {
            parkingSlots.add(new ParkingSlot(i+1, ParkingSlotStatus.AVAILABLE));
        }
        Collections.sort(parkingSlots);
    }

    @Override
    public int park(Vehicle vehicle) {
        if (!ParkingSlotUtil.isVehicleAlreadyParked(parkingSlots, vehicle.getRegistrationNum())) {
            ParkingSlot slot = ParkingSlotUtil.getNearestAvailableParkingSlot(parkingSlots);
            if (slot != null) {
                ParkingSlotUtil.bookSlot(slot, vehicle);
                return slot.getSlotNumber();
            } else {
                return NOT_AVAILABLE;
            }
        }
        return VEHICLE_ALREADY_PARKED;
    }

    @Override
    public int leaveCar(int slotNum) {
        if (slotNum < parkingSlots.size()) {
            ParkingSlotUtil.setSlotAvailable(parkingSlots, slotNum);
            return SUCCESS;
        }
        return INVALID_SLOT;
    }

    @Override
    public List<String> getStatus() {
        List<String> statusList = new ArrayList<>();
        for (ParkingSlot slot: parkingSlots) {
            if (slot.getVehicle() != null && slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)) {
                statusList.add(slot.getSlotNumber() + "\t\t" + slot.getVehicle().getRegistrationNum() + "\t\t" + slot.getVehicle().getColor());
            }
        }
        return statusList;
    }

    @Override
    public List<String> getRegistNumWithColor(String color) {
        List<String> registrationNumList = new ArrayList<String>();
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED) && slot.getVehicle() != null && slot.getVehicle().getColor().equalsIgnoreCase(color)) {
                registrationNumList.add(slot.getVehicle().getRegistrationNum());
            }
        }
        return registrationNumList;
    }

    @Override
    public List<Integer> getSlotNumOfCarWithColor(String color) {
        List<Integer> slotNumList = new ArrayList<Integer>();
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED) && slot.getVehicle() != null && slot.getVehicle().getColor().equalsIgnoreCase(color)) {
                slotNumList.add(slot.getSlotNumber());
            }
        }
        return slotNumList;
    }

    @Override
    public int getSlotNumWithRegNum(String registrationNum) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED) && slot.getVehicle() != null && slot.getVehicle().getRegistrationNum().equalsIgnoreCase(registrationNum)) {
                return slot.getSlotNumber();
            }
        }
        return NOT_AVAILABLE;
    }
}
