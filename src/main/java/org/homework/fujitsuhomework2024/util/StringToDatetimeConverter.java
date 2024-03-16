package org.homework.fujitsuhomework2024.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class StringToDatetimeConverter implements Converter<String, LocalDateTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm");

    public LocalDateTime convert(String source){
        if(source.equals("missing")) return LocalDateTime.now();
        return LocalDateTime.parse(source, formatter);
    }
}
