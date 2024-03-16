package org.homework.fujitsuhomework2024.repository;

import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Station, Long> {
    @Query("SELECT s FROM Station s WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM Station s2 WHERE s2.timestamp <= :customTimestamp) AND s.name = :cityName")
    Station findStationByNameAndTime(@Param("customTimestamp") Timestamp customTimestamp, @Param("cityName") String cityName);
}
