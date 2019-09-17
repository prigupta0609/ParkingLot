package com.company.parkinglot.dao;

import com.company.parkinglot.constant.Constants;
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

    public static ParkingLotManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ParkingLotManagerImpl.class) {
                if (instance == null) {
                    instance = new ParkingLotManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public int createParkingLot(int numOfSlots) {
        parkingSlots = new ArrayList<ParkingSlot>(numOfSlots);
        for (int i=0; i<numOfSlots; i++) {
            parkingSlots.add(new ParkingSlot(i+1, ParkingSlotStatus.AVAILABLE));
        }
        Collections.sort(parkingSlots);
        if (parkingSlots.size() == numOfSlots) {
            return Constants.SUCCESS;
        }
        return Constants.FAILED;
    }

    @Override
    public int park(Vehicle vehicle) {
        if (parkingSlots != null && !ParkingSlotUtil.isVehicleAlreadyParked(parkingSlots, vehicle.getRegistrationNum())) {
            ParkingSlot slot = ParkingSlotUtil.getNearestAvailableParkingSlot(parkingSlots);
            if (slot != null) {
                ParkingSlotUtil.bookSlot(slot, vehicle);
                return slot.getSlotNumber();
            } else {
                return Constants.NOT_AVAILABLE;
            }
        }
        return Constants.VEHICLE_ALREADY_PARKED;
    }

    @Override
    public int leaveCar(int slotNum) {
        if (parkingSlots != null && slotNum < parkingSlots.size()) {
            ParkingSlotUtil.setSlotAvailable(parkingSlots, slotNum);
            return Constants.SUCCESS;
        }
        return Constants.INVALID_SLOT;
    }

    @Override
    public List<String> getStatus() {
        if (parkingSlots != null) {
            List<String> statusList = new ArrayList<>();
            for (ParkingSlot slot : parkingSlots) {
                if (slot.getVehicle() != null && slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)) {
                    statusList.add(slot.getSlotNumber() + "\t\t" + slot.getVehicle().getRegistrationNum() + "\t\t" + slot.getVehicle().getColor());
                }
            }
            return statusList;
        }
        return null;
    }

    @Override
    public List<String> getRegistNumWithColor(String color) {
        if (parkingSlots != null){
            List<String> registrationNumList = new ArrayList<String>();
            for (ParkingSlot slot : parkingSlots) {
                if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED) && slot.getVehicle() != null && slot.getVehicle().getColor().equalsIgnoreCase(color)) {
                    registrationNumList.add(slot.getVehicle().getRegistrationNum());
                }
            }
            return registrationNumList;
        }
        return null;
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
        return Constants.NOT_AVAILABLE;
    }
}
