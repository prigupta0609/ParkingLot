package com.company.parkinglot.validate;

import com.company.parkinglot.constant.Constants;

public class RequestValidator {

    public static boolean isValidRequest(String input) {
        String[] requestParam = input.split(" ");
        if (requestParam.length > 0) {
            switch (requestParam[0]) {
                case Constants.STATUS : return requestParam.length == 1;
                case Constants.CREATE_PARKING_LOT :
                case Constants.LEAVE :
                case Constants.REG_NUM_FOR_COLOR :
                case Constants.SLOT_NUM_FOR_COLOR :
                case Constants.SLOT_NUM_FOR_REGIS_NUM : return requestParam.length == 2;
                case Constants.PARK : return requestParam.length == 3;
                default: return false;
            }
        }
        return false;
    }
}
