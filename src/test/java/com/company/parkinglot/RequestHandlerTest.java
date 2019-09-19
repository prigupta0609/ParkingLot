package com.company.parkinglot;

import com.company.parkinglot.constant.Constants;
import com.company.parkinglot.exception.ParkingException;
import com.company.parkinglot.service.RequestHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RequestHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final RequestHandler requestHandler = RequestHandler.getInstance();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void resetParkingLot() throws Exception {
        requestHandler.handle(Constants.RESET_PARKING_LOT);
    }

    @Test
    public void testCreateParkingLot() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 10");
        Assert.assertEquals("Created a parking lot with 10 slots", outContent.toString().trim());
    }

    @Test()
    public void testParkingAlreadyExist() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 1");
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        Assert.assertEquals("Created a parking lot with 1 slots\nParking lot already exists", outContent.toString().trim());
    }

    @Test
    public void testPark() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        Assert.assertEquals("Created a parking lot with 2 slots\nAllocated slot number: 1\nAllocated slot number: 2", outContent.toString().trim());
    }

    @Test(expected = ParkingException.class)
    public void testParkLotFull() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.PARK + " RJ-22-BD-4341 GREEN");
    }

    @Test(expected = ParkingException.class)
    public void testParkAlreadyParkedVehicle() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 7");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
    }

    @Test
    public void testLeave() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.LEAVE + " 1");
        Assert.assertEquals("Created a parking lot with 2 slots\nAllocated slot number: 1\nSlot number 1 is free", outContent.toString().trim());
    }

    @Test(expected = ParkingException.class)
    public void testLeaveFailure() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.LEAVE + " 4");
    }

    @Test
    public void testStatus() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 4");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.STATUS);
        Assert.assertEquals("Created a parking lot with 4 slots\nAllocated slot number: 1\nAllocated slot number: 2\nSlot No.    Registration No    Colour\n1           RJ-11-AA-1000      RED\n" +
                "2           RJ-22-BB-4343      YELLOW", outContent.toString().trim());
    }

    @Test
    public void testRegisNumWithColor() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 3");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-3210 RED");
        requestHandler.handle(Constants.REG_NUM_FOR_COLOR + " RED");
        Assert.assertEquals("Created a parking lot with 3 slots\nAllocated slot number: 1\nAllocated slot number: 2\nAllocated slot number: 3\nRJ-11-AA-1000, RJ-11-AA-3210", outContent.toString().trim());
    }

    @Test
    public void testSlotNumForColor() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 3");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-3210 RED");
        requestHandler.handle(Constants.SLOT_NUM_FOR_COLOR + " RED");
        Assert.assertEquals("Created a parking lot with 3 slots\nAllocated slot number: 1\nAllocated slot number: 2\nAllocated slot number: 3\n1, 3", outContent.toString().trim());
    }

    @Test
    public void testSlotNumWithRegNum() throws Exception {
        requestHandler.handle(Constants.CREATE_PARKING_LOT + " 2");
        requestHandler.handle(Constants.PARK + " RJ-11-AA-1000 RED");
        requestHandler.handle(Constants.PARK + " RJ-22-BB-4343 YELLOW");
        requestHandler.handle(Constants.SLOT_NUM_FOR_REGIS_NUM + " RJ-22-BB-4343");
        Assert.assertEquals("Created a parking lot with 2 slots\nAllocated slot number: 1\nAllocated slot number: 2\n2", outContent.toString().trim());
    }
}
