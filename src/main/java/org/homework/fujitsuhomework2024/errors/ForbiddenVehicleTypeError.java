package org.homework.fujitsuhomework2024.errors;

public class ForbiddenVehicleTypeError extends RuntimeException{
    public ForbiddenVehicleTypeError() {
        super("Usage of selected vehicle type is forbidden");
    }
}
