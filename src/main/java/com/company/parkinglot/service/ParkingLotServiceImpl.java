package com.company.parkinglot.service;

import com.company.parkinglot.constant.Constants;
import com.company.parkinglot.contract.Car;
import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.dao.ParkingLotManager;
import com.company.parkinglot.dao.ParkingLotManagerImpl;
import com.company.parkinglot.exception.InvalidRequestException;
import com.company.parkinglot.exception.ParkingException;

import java.util.List;
import java.util.StringJoiner;

public class ParkingLotServiceImpl implements ParkingLotService {

    private static ParkingLotServiceImpl instance = null;
    private static final ParkingLotManager parkingLotManager = ParkingLotManagerImpl.getInstance();

    public static ParkingLotServiceImpl getInstance() {
        if (instance == null) {
            synchronized (ParkingLotServiceImpl.class) {
                if (instance == null) {
                    instance = new ParkingLotServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void createParkingLot(int numOfSlots) throws InvalidRequestException {
        if (numOfSlots > 0) {
            String result = parkingLotManager.createParkingLot(numOfSlots);
            if (result == Constants.OPERATION_SUCCESS) {
                System.out.println("Created a parking lot with " + numOfSlots + " slots");
            } else if (result == Constants.PARKING_LOT_ALREADY_EXIST) {
                System.out.println("Parking lot already exists");
            }
        } else {
            throw new InvalidRequestException("Initiate parking lot with valid number of slots");
        }
    }

    @Override
    public void park(String registrationNum, String color) throws ParkingException {
        ParkingSlot slot = parkingLotManager.park(new Car(registrationNum, color));
        if (slot != null) {
            System.out.println("Allocated slot number: " + slot.getSlotNumber());
        }
    }

    @Override
    public void leaveCar(int slotNum) throws ParkingException {
        if (parkingLotManager.leaveCar(slotNum) == Constants.INVALID_SLOT) {
            throw new ParkingException("This slot number don't exist");
        } else {
            System.out.println("Slot number " + slotNum + " is free");
        }
    }

    @Override
    public void getStatus() throws ParkingException {
        List<String> statusList = parkingLotManager.getStatus();
        if (statusList != null) {
            System.out.println("Slot No.    Registration No    Colour");
            for (String status: statusList) {
                System.out.println(status);
            }
        } else {
            throw new ParkingException("Parking lot is empty");
        }
    }

    @Override
    public void getRegistNumWithColor(String color) throws ParkingException {
        List<String> registrationList = parkingLotManager.getRegistNumWithColor(color);
        if (registrationList != null) {
            System.out.println(String.join(", ", registrationList));
        } else {
            throw new ParkingException("Cars with color " + color + " not parked");
        }
    }

    @Override
    public void getSlotNumOfCarWithColor(String color) throws ParkingException {
        List<Integer> slotList = parkingLotManager.getSlotNumOfCarWithColor(color);
        if (slotList != null) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Integer slot : slotList) {
                joiner.add(slot + "");
            }
            System.out.println(joiner.toString());
        } else {
            throw new ParkingException("Cars with color " + color + " not parked");
        }
    }

    @Override
    public void getSlotNumWithRegNum(String registrationNum) throws ParkingException {
        int slotNum = parkingLotManager.getSlotNumWithRegNum(registrationNum);
        if (slotNum == Constants.NOT_AVAILABLE) {
            throw new ParkingException("Not found");
        }
        System.out.println(slotNum);
    }

    @Override
    public void resetParkingLot() {
        parkingLotManager.resetParkingLot();
    }
}
