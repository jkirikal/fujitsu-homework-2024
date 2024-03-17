package org.homework.fujitsuhomework2024.util;

import org.homework.fujitsuhomework2024.enums.VehicleType;
import org.springframework.core.convert.converter.Converter;

public class StringToVehicleTypeConverter implements Converter<String, VehicleType> {
    @Override
    public VehicleType convert(String source){
        return VehicleType.valueOf(source.toUpperCase());
    }
}
