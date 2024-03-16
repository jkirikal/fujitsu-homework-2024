package org.homework.fujitsuhomework2024;

import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.enums.VehicleType;
import org.homework.fujitsuhomework2024.errors.ForbiddenVehicleTypeError;
import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.Station;
import org.homework.fujitsuhomework2024.service.BusinessRulesService;
import org.homework.fujitsuhomework2024.service.FeeCalculationService;
import org.homework.fujitsuhomework2024.service.WeatherService;
import org.homework.fujitsuhomework2024.util.BusinessRulesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FeeCalculationTests {


	@MockBean
	private BusinessRulesMapper businessRulesMapper;

	@MockBean
	private WeatherService weatherService;

	@MockBean
	private BusinessRulesService businessRulesService;

	@Autowired
	private FeeCalculationService feeCalculationService;



    @BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void calculate_fee_base_conditions() throws ForbiddenVehicleTypeError {
		LocalDateTime datetime = LocalDateTime.now();
		Station mockedStation = Station.builder()
				.id(1L)
				.airTemperature(2)
				.name("Tallinn-Harku")
				.phenomenon("misty")
				.timestamp(Timestamp.valueOf(datetime))
				.windSpeed(15)
				.wmoCode(30)
				.build();


		BusinessRules mockedRules = BusinessRules.builder()
				.id(1L)
				.vehicle_bike(0)
				.vehicle_car(1)
				.vehicle_scooter(0.5F)
				.city_tallinn(3)
				.city_tartu(2.5F)
				.city_parnu(2)
				.ATEF_less_than_minus_10(1)
				.ATEF_less_than_zero(0.5F)
				.WSEF_more_than_20(-1)
				.WSEF_more_than_10(0.5F)
				.WPEF_snow_sleet(1)
				.WPEF_rain(0.5F)
				.WPEF_glaze_hail_thunder(-1)
				.timestamp(Timestamp.valueOf(datetime))
				.build();
		when(weatherService.weatherFromDatabase(datetime, City.TALLINN)).thenReturn(mockedStation);
		when(businessRulesService.BusinessRulesByTimestamp(mockedStation.getTimestamp())).thenReturn(mockedRules);

		float fee = feeCalculationService.calculateFee(City.TALLINN, VehicleType.CAR, datetime);
		float fee2 = feeCalculationService.calculateFee(City.TALLINN, VehicleType.SCOOTER, datetime);
		assertNotNull(fee);
		assertEquals(4.0F, fee);
		assertEquals(3.5F, fee2);
	}
	@Test
	void calculate_fee_thunder() throws ForbiddenVehicleTypeError {
		LocalDateTime datetime = LocalDateTime.now();
		Station mockedStation = Station.builder()
				.id(1L)
				.airTemperature(-15)
				.name("Tallinn-Harku")
				.phenomenon("thunder")
				.timestamp(Timestamp.valueOf(datetime))
				.windSpeed(15)
				.wmoCode(30)
				.build();


		BusinessRules mockedRules = BusinessRules.builder()
				.id(1L)
				.vehicle_bike(0)
				.vehicle_car(1)
				.vehicle_scooter(0.5F)
				.city_tallinn(3)
				.city_tartu(2.5F)
				.city_parnu(2)
				.ATEF_less_than_minus_10(1)
				.ATEF_less_than_zero(0.5F)
				.WSEF_more_than_20(-1)
				.WSEF_more_than_10(0.5F)
				.WPEF_snow_sleet(1)
				.WPEF_rain(0.5F)
				.WPEF_glaze_hail_thunder(-1)
				.timestamp(Timestamp.valueOf(datetime))
				.build();
		when(weatherService.weatherFromDatabase(datetime, City.TALLINN)).thenReturn(mockedStation);
		when(businessRulesService.BusinessRulesByTimestamp(mockedStation.getTimestamp())).thenReturn(mockedRules);

		assertThrows(ForbiddenVehicleTypeError.class,() -> feeCalculationService.calculateFee(City.TALLINN, VehicleType.BIKE, datetime));
	}
	@Test
	void calculate_fee_different_conditions() throws ForbiddenVehicleTypeError {
		LocalDateTime datetime = LocalDateTime.now();
		Station mockedStation = Station.builder()
				.id(1L)
				.airTemperature(-15)
				.name("Tallinn-Harku")
				.phenomenon("snow")
				.timestamp(Timestamp.valueOf(datetime))
				.windSpeed(15)
				.wmoCode(30)
				.build();


		BusinessRules mockedRules = BusinessRules.builder()
				.id(1L)
				.vehicle_bike(0)
				.vehicle_car(1)
				.vehicle_scooter(0.5F)
				.city_tallinn(3)
				.city_tartu(2.5F)
				.city_parnu(2)
				.ATEF_less_than_minus_10(1)
				.ATEF_less_than_zero(0.5F)
				.WSEF_more_than_20(-1)
				.WSEF_more_than_10(0.5F)
				.WPEF_snow_sleet(1)
				.WPEF_rain(0.5F)
				.WPEF_glaze_hail_thunder(-1)
				.timestamp(Timestamp.valueOf(datetime))
				.build();
		when(weatherService.weatherFromDatabase(datetime, City.TALLINN)).thenReturn(mockedStation);
		when(businessRulesService.BusinessRulesByTimestamp(mockedStation.getTimestamp())).thenReturn(mockedRules);
		float fee1 = feeCalculationService.calculateFee(City.TALLINN, VehicleType.BIKE, datetime);
		float fee2 = feeCalculationService.calculateFee(City.TALLINN, VehicleType.SCOOTER, datetime);
		float fee3 = feeCalculationService.calculateFee(City.TALLINN, VehicleType.CAR, datetime);

		assertEquals(5.5F, fee1);
		assertEquals(5.5F, fee2);
		assertEquals(4.0F, fee3);

	}

}
