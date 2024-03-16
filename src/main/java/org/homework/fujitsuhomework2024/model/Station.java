package org.homework.fujitsuhomework2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@XmlRootElement(name = "station")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int wmoCode;
    private float airTemperature;
    private float windSpeed;
    private String phenomenon;
    private Timestamp timestamp;

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "wmocode")
    public void setWmoCode(int wmoCode) {
        this.wmoCode = wmoCode;
    }

    @XmlElement(name = "airtemperature")
    public void setAirTemperature(float airTemperature) {
        this.airTemperature = airTemperature;
    }

    @XmlElement(name = "windspeed")
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    @XmlElement(name = "phenomenon")
    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

}
