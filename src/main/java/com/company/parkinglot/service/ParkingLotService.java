package com.company.parkinglot.service;

import com.company.parkinglot.exception.InvalidRequestException;
import com.company.parkinglot.exception.ParkingException;

public interface ParkingLotService {

    public void createParkingLot(int numOfSlots) throws InvalidRequestException;
    public void park (String registrationNum, String color) throws ParkingException;
    public void leaveCar (int slotNum) throws ParkingException;
    public void getStatus() throws ParkingException;
    public void getRegistNumWithColor (String color) throws ParkingException;
    public void getSlotNumOfCarWithColor (String color) throws ParkingException;
    public void getSlotNumWithRegNum (String registrationNum) throws ParkingException;
    public void resetParkingLot();
}
