package org.homework.fujitsuhomework2024.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BusinessRulesDto {
    private float vehicle_car;
    private float vehicle_scooter;
    private float vehicle_bike;
    private float city_tallinn;
    private float city_tartu;
    private float city_parnu;
    private float ATEF_less_than_minus_10;
    private float ATEF_less_than_zero;
    private float WSEF_more_than_20;
    private float WSEF_more_than_10;
    private float WPEF_snow_sleet;
    private float WPEF_rain;
    private float WPEF_glaze_hail_thunder;
}
