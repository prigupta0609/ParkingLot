package com.company.parkinglot.service;

import com.company.parkinglot.constant.Constants;
import com.company.parkinglot.exception.ExceptionEnum;
import com.company.parkinglot.exception.InvalidRequestException;
import com.company.parkinglot.exception.ParkingException;

public class RequestHandler {

    private static final ParkingLotService parkingLotService = ParkingLotServiceImpl.getInstance();
    private static RequestHandler instance = null;

    public static RequestHandler getInstance() {
        if (instance == null) {
            synchronized (RequestHandler.class) {
                if (instance == null) {
                    instance = new RequestHandler();
                }
            }
        }
        return instance;
    }

    private RequestHandler() {}

    public void handle(String input) throws InvalidRequestException, ParkingException {
        String[] requestParam = input.split(" ");
        switch (requestParam[0]) {
            case Constants.CREATE_PARKING_LOT :
                try {
                    parkingLotService.createParkingLot(Integer.parseInt(requestParam[1]));
                } catch (NumberFormatException exception) {
                    throw new InvalidRequestException(ExceptionEnum.INVALID_REQUEST_EXCEPTION.getErrorMsg());
                }
                break;
            case Constants.LEAVE :
                try {
                    parkingLotService.leaveCar(Integer.parseInt(requestParam[1]));
                } catch (NumberFormatException exception) {
                    throw new InvalidRequestException(ExceptionEnum.INVALID_REQUEST_EXCEPTION.getErrorMsg());
                }
                break;
            case Constants.PARK :
                parkingLotService.park(requestParam[1], requestParam[2]);
                break;
            case Constants.REG_NUM_FOR_COLOR :
                parkingLotService.getRegistNumWithColor(requestParam[1]);
                break;
            case Constants.SLOT_NUM_FOR_COLOR :
                parkingLotService.getSlotNumOfCarWithColor(requestParam[1]);
                break;
            case Constants.SLOT_NUM_FOR_REGIS_NUM :
                parkingLotService.getSlotNumWithRegNum(requestParam[1]);
                break;
            case Constants.STATUS :
                parkingLotService.getStatus();
                break;
            case Constants.RESET_PARKING_LOT :
                parkingLotService.resetParkingLot();
                break;
            default: throw new InvalidRequestException("Invalid request operation : " + requestParam[0]);
        }
    }
}
