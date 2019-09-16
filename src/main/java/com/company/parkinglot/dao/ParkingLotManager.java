package com.company.parkinglot.dao;

import com.company.parkinglot.contract.Vehicle;

import java.util.List;

public interface ParkingLotManager {

    public int park (Vehicle vehicle);
    public int leaveCar (int slotNum);
    public List<String> getStatus();
    public List<String> getRegistNumWithColor (String color);
    public List<Integer> getSlotNumOfCarWithColor (String color);
    public int getSlotNumWithRegNum (String registrationNum);
}
