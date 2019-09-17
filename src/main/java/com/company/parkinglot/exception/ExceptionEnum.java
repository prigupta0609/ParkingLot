package com.company.parkinglot.exception;

public enum ExceptionEnum {
    INVALID_REQUEST_EXCEPTION (1, "Invalid request parameters"),
    VEHICLE_ALREADY_PARKED (2, "Can't park already parked vehicle"),
    PARKING_FULL (3, "Parking lot is full");

    private int errorCode;
    private String errorMsg;

    private ExceptionEnum (int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
