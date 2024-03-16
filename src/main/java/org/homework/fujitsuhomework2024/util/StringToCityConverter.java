package org.homework.fujitsuhomework2024.util;

import org.homework.fujitsuhomework2024.enums.City;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToCityConverter implements Converter<String, City> {
    @Override
    public City convert(String source){
        return City.valueOf(source.toUpperCase());
    }
}
