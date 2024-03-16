package org.homework.fujitsuhomework2024.service;

import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.enums.VehicleType;
import org.homework.fujitsuhomework2024.errors.ForbiddenVehicleTypeError;
import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeeCalculationService {
    private final WeatherService weatherService;
    private final BusinessRulesService businessRulesService;

    @Autowired
    public FeeCalculationService(WeatherService weatherService, BusinessRulesService businessRulesService) {
        this.weatherService = weatherService;
        this.businessRulesService = businessRulesService;
    }

    /**
     * Calculates the delivery fee for a specific vehicle in a city at a specific time.
     * The fee is calculated based on weather conditions, vehicle type, and city-specific
     * business rules.
     *
     * @param city The city where the fee is being calculated.
     * @param vehicleType The type of vehicle for which the fee is calculated.
     * @param datetime The time and date of the delivery fee calculation.
     * @return The calculated delivery fee.
     * @throws ForbiddenVehicleTypeError if the vehicle type is not allowed under certain weather conditions.
     * @throws RuntimeException if no business rules are found for the given timestamp.
     */
    public float calculateFee(City city, VehicleType vehicleType, LocalDateTime datetime) throws ForbiddenVehicleTypeError, RuntimeException{
        Station s = weatherService.weatherFromDatabase(datetime, city);
        if(s==null) throw new RuntimeException("No data found for the station at the requested time");
        BusinessRules b = businessRulesService.BusinessRulesByTimestamp(s.getTimestamp());
        if(b==null) throw new RuntimeException("No business rules found for the timestamp");
        float ATEF = calculateATEF(vehicleType, s.getAirTemperature(),b);
        float WSEF = calculateWSEF(vehicleType, s.getWindSpeed(), b);
        float WPEF = calculateWPEF(vehicleType, s.getPhenomenon(), b);
        if(ATEF==-1||WSEF==-1||WPEF==-1) throw new ForbiddenVehicleTypeError();
        return cityFee(city, b) +
                vehicleFee(vehicleType,b) +
                ATEF +
                WSEF +
                WPEF;
    }
    private float cityFee(City city, BusinessRules b){
        return switch (city) {
            case TALLINN -> b.getCity_tallinn();
            case TARTU -> b.getCity_tartu();
            case PÄRNU -> b.getCity_parnu();
        };
    }

    private float vehicleFee(VehicleType vehicleType, BusinessRules b){
        return switch (vehicleType) {
            case CAR -> b.getVehicle_car();
            case SCOOTER -> b.getVehicle_scooter();
            case BIKE -> b.getVehicle_bike();
        };
    }

    private float calculateATEF(VehicleType vehicleType, float airTemp, BusinessRules b){
        if(vehicleType==VehicleType.SCOOTER||vehicleType==VehicleType.BIKE){
            if(airTemp<-10) return b.getATEF_less_than_minus_10();
            else if(airTemp<=0) return b.getATEF_less_than_zero();
        }
        return 0;
    }

    private float calculateWSEF(VehicleType vehicleType, float windSpeed, BusinessRules b){
        if(vehicleType==VehicleType.BIKE){
            if(windSpeed>20) return b.getWSEF_more_than_20();
            else if(windSpeed>=10) return b.getWSEF_more_than_10();
        }
        return 0;
    }

    private float calculateWPEF(VehicleType vehicleType,String phenomenon, BusinessRules b){
        if(vehicleType==VehicleType.SCOOTER||vehicleType==VehicleType.BIKE){
            if(phenomenon.matches(".*([Ss]now|[Ss]leet).*")) return b.getWPEF_snow_sleet();
            else if(phenomenon.matches(".*[Rr]ain.*")) return b.getWPEF_rain();
            else if(phenomenon.matches(".*([Gg]laze|[Hh]ail|[Tt]hunder).*")) return b.getWPEF_glaze_hail_thunder();
        }
        return 0;
    }
}
