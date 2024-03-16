package org.homework.fujitsuhomework2024.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.util.List;

@Getter
@XmlRootElement(name = "observations")
public class Observations {
    private String timestamp;
    private List<Station> observations;

    @XmlAttribute(name = "timestamp") // This binds the Java field to the XML attribute
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @XmlElement(name = "station")
    public void setObservations(List<Station> observations) {
        this.observations = observations;
    }
}
