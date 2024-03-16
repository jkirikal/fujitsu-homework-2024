package org.homework.fujitsuhomework2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Timestamp timestamp;
}
