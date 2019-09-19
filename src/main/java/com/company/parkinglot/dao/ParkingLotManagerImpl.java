package com.company.parkinglot.dao;

import com.company.parkinglot.constant.Constants;
import com.company.parkinglot.contract.ParkingLot;
import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.contract.ParkingSlotStatus;
import com.company.parkinglot.contract.Vehicle;
import com.company.parkinglot.dao.util.ParkingSlotUtil;
import com.company.parkinglot.exception.ParkingException;

import java.util.*;

public class ParkingLotManagerImpl implements ParkingLotManager {

    private static ParkingLotManagerImpl instance = null;
//    private static List<ParkingSlot> parkingSlots = null;
    private static ParkingLot parkingLot = null;

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
    public String createParkingLot(int numOfSlots) {
        if (parkingLot == null) {
            parkingLot = ParkingLot.getInstance(numOfSlots);
//            parkingSlots = new ArrayList<ParkingSlot>(numOfSlots);
//            for (int i = 0; i < numOfSlots; i++) {
//                parkingSlots.add(new ParkingSlot(i + 1, ParkingSlotStatus.AVAILABLE));
//            }
//            Collections.sort(parkingSlots);
            if (parkingLot.getParkingSlots().size() == numOfSlots) {
                return Constants.OPERATION_SUCCESS;
            }
        } else {
            return Constants.PARKING_LOT_ALREADY_EXIST;
        }
        return Constants.UNKNOWN_EXCEPTION_OCCURRED;
    }

    @Override
    public ParkingSlot park(Vehicle vehicle) throws ParkingException {
        if (parkingLot != null) {
            if (parkingLot.getParkingSlots() != null && !ParkingSlotUtil.isVehicleAlreadyParked(parkingLot.getParkingSlots().values(), vehicle.getRegistrationNum())) {
                Set<ParkingSlot> parkingSlots = parkingLot.getParkingSlots().keySet();
                ParkingSlot slot = ParkingSlotUtil.getNearestAvailableParkingSlot(parkingSlots);
                if (slot != null) {
                    ParkingSlotUtil.bookSlot(parkingLot, slot, vehicle);
                    return slot;
                } else {
                    throw new ParkingException(Constants.PARKING_LOT_FULL);
                }
            }
            throw new ParkingException(Constants.VEHICLE_ALREADY_PARKED);
        } else {
            throw new ParkingException(Constants.PARKING_LOT_DOES_NOT_EXISTS);
        }
    }

    @Override
    public int leaveCar(int slotNum) {
        if (parkingLot != null) {
            if (parkingLot.getParkingSlots() != null && slotNum < parkingLot.getParkingSlots().size()) {
                ParkingSlotUtil.setSlotAvailable(parkingLot.getParkingSlots(), slotNum);
                return Constants.SUCCESS;
            }
            return Constants.INVALID_SLOT;
        } else {
            System.out.println(Constants.PARKING_LOT_DOES_NOT_EXISTS);
        }
        return Constants.FAILURE;
    }

    @Override
    public List<String> getStatus() {
        if (parkingLot != null && parkingLot.getParkingSlots() != null) {
            List<String> statusList = new ArrayList<>();
            for (ParkingSlot slot : parkingLot.getParkingSlots().keySet()) {
                if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)) {
                    Vehicle vehicle = parkingLot.getParkingSlots().get(slot);
                    statusList.add(slot.getSlotNumber() + "           " + vehicle.getRegistrationNum() + "      " + vehicle.getColor());
                }
            }
            return statusList;
        }
        return null;
    }

    @Override
    public List<String> getRegistNumWithColor(String color) {
        if (parkingLot != null && parkingLot.getParkingSlots() != null) {
            List<String> registrationNumList = new ArrayList<String>();
            for (ParkingSlot slot : parkingLot.getParkingSlots().keySet()) {
                if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)
                        && parkingLot.getParkingSlots().get(slot) != null
                        && parkingLot.getParkingSlots().get(slot).getColor().equalsIgnoreCase(color)) {
                    registrationNumList.add(parkingLot.getParkingSlots().get(slot).getRegistrationNum());
                }
            }
            return registrationNumList;
        }
        return null;
    }

    @Override
    public List<Integer> getSlotNumOfCarWithColor(String color) {
        if (parkingLot != null && parkingLot.getParkingSlots() != null) {
            List<Integer> slotNumList = new ArrayList<Integer>();
            for (ParkingSlot slot : parkingLot.getParkingSlots().keySet()) {
                if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)
                        && parkingLot.getParkingSlots().get(slot) != null
                        && parkingLot.getParkingSlots().get(slot).getColor().equalsIgnoreCase(color)) {
                    slotNumList.add(slot.getSlotNumber());
                }
            }
            return slotNumList;
        }
        return null;
    }

    @Override
    public int getSlotNumWithRegNum(String registrationNum) {
        if (parkingLot != null && parkingLot.getParkingSlots() != null) {
            for (ParkingSlot slot : parkingLot.getParkingSlots().keySet()) {
                if (slot.getParkingSlotStatus().equals(ParkingSlotStatus.OCCUPIED)
                        && parkingLot.getParkingSlots().get(slot) != null
                        && parkingLot.getParkingSlots().get(slot).getRegistrationNum().equalsIgnoreCase(registrationNum)) {
                    return slot.getSlotNumber();
                }
            }
        }
        return Constants.NOT_AVAILABLE;
    }

    @Override
    public void resetParkingLot() {
        parkingLot.clear();
        parkingLot = null;
    }
}
