package com.company.parkinglot.dao;

import com.company.parkinglot.constant.Constants;
import com.company.parkinglot.contract.Car;
import com.company.parkinglot.contract.ParkingSlot;
import com.company.parkinglot.exception.ParkingException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ParkingLotManagerImplTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ParkingLotManagerImpl requestHandler = ParkingLotManagerImpl.getInstance();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void resetParkingLot() throws Exception {
        requestHandler.resetParkingLot();
    }

    @Test
    public void testcreateParkingLot() {
        Assert.assertEquals(Constants.OPERATION_SUCCESS, requestHandler.createParkingLot(3));
        Assert.assertEquals(Constants.PARKING_LOT_ALREADY_EXIST, requestHandler.createParkingLot(1));
    }

    @Test
    public void testPark() throws ParkingException {
        requestHandler.createParkingLot(3);
        ParkingSlot slot1 = requestHandler.park(new Car("RJ-12-123", "White"));
        Assert.assertEquals(1, slot1.getSlotNumber());
        ParkingSlot slot2 = requestHandler.park(new Car("RJ-15-223", "White"));
        Assert.assertEquals(2, slot2.getSlotNumber());
    }

    @Test (expected = ParkingException.class)
    public void testAlreadyParkedVehicle() throws ParkingException {
        requestHandler.createParkingLot(2);
        ParkingSlot slot1 = requestHandler.park(new Car("RJ-12-123", "White"));
        Assert.assertEquals(1, slot1.getSlotNumber());
        ParkingSlot slot2 = requestHandler.park(new Car("RJ-12-123", "White"));
    }
}
