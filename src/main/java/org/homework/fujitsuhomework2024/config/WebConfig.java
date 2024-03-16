package org.homework.fujitsuhomework2024.config;

import org.homework.fujitsuhomework2024.util.StringToCityConverter;
import org.homework.fujitsuhomework2024.util.StringToDatetimeConverter;
import org.homework.fujitsuhomework2024.util.StringToVehicleTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCityConverter());
        registry.addConverter(new StringToVehicleTypeConverter());
        registry.addConverter(new StringToDatetimeConverter());
    }
}
