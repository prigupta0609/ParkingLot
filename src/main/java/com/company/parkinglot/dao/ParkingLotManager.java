package com.company.parkinglot.dao;

import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.contract.Vehicle;
import com.company.parkinglot.exception.ParkingException;

import java.util.List;

public interface ParkingLotManager {

    public String createParkingLot (int numOfSlots);
    public ParkingSlot park (Vehicle vehicle) throws ParkingException;
    public int leaveCar (int slotNum);
    public List<String> getStatus();
    public List<String> getRegistNumWithColor (String color);
    public List<Integer> getSlotNumOfCarWithColor (String color);
    public int getSlotNumWithRegNum (String registrationNum);
    public void resetParkingLot();
}
