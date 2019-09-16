package com.company.parkinglot.contract;

public abstract class Vehicle {

    private String registrationNum = null;
    private String color = null;

    public Vehicle (String registrationNum, String color) {
        this.registrationNum = registrationNum;
        this.color = color;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNum='" + registrationNum + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
