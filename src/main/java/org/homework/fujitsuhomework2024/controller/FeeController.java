package org.homework.fujitsuhomework2024.controller;

import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.enums.VehicleType;
import org.homework.fujitsuhomework2024.errors.ForbiddenVehicleTypeError;
import org.homework.fujitsuhomework2024.service.FeeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class FeeController {
    private static final Logger logger = LoggerFactory.getLogger(FeeController.class);
    private final FeeCalculationService feeCalculationService;

    @Autowired
    public FeeController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }


    /**
     * Endpoint to calculate and retrieve the delivery fee for a vehicle in a specific city at a given time.
     *
     * @param city The city for which the fee calculation is requested.
     * @param vehicleType The type of vehicle for which the fee is being calculated.
     * @param datetime The date and time for which the fee is calculated. If not specified, the current datetime is used.
     * @return A ResponseEntity containing the calculated fee or an error message in case of failure.
     */
    @GetMapping("/api")
    public ResponseEntity<?> getFee(@RequestParam("City")City city,
                                    @RequestParam("Vehicle type")VehicleType vehicleType,
                                    @RequestParam(value = "Datetime", required = false, defaultValue = "missing") LocalDateTime datetime){
        try{
            return ResponseEntity.ok(feeCalculationService.calculateFee(city, vehicleType, datetime));
        }
        catch (ForbiddenVehicleTypeError e){
            logger.warn("Forbidden vehicle type error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e){
            logger.error("Error calculating fee");
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
