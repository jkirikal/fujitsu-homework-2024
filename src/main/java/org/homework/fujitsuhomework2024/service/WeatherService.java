package org.homework.fujitsuhomework2024.service;

import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.model.Observations;
import org.homework.fujitsuhomework2024.model.Station;
import org.homework.fujitsuhomework2024.repository.WeatherRepository;
import org.homework.fujitsuhomework2024.util.UnixTimestampConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;
    private final UnixTimestampConverter unixTimestampConverter;

    @Autowired
    public WeatherService(RestTemplate restTemplate, WeatherRepository weatherRepository, UnixTimestampConverter unixTimestampConverter) {
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
        this.unixTimestampConverter = unixTimestampConverter;
    }

    private String fetchWeatherData(){
        String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        return restTemplate.getForObject(url, String.class);
    }

    private Observations parseXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Observations.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Observations) unmarshaller.unmarshal(new StringReader(xml));
    }

    /**
     * Initiates the fetching and saving of weather data from a predefined URL.
     * This method is scheduled to run based on the cron expression defined in the application properties.
     * It fetches weather data, parses the XML, filters for relevant cities, converts timestamps,
     * and saves each observation to the repository.
     *
     */
    @PostConstruct
    @Scheduled(cron = "${scheduling.job.cron}")
    public void fetchAndSaveWeatherData() {
        String xml = fetchWeatherData();
        try{
            Observations observations = parseXml(xml);
            Set<String> relevantCities = new HashSet<>(Arrays.asList("Tallinn-Harku","Tartu-Tõravere","Pärnu"));
            for (Station observation : observations.getObservations()) {
                if(relevantCities.contains(observation.getName())){
                    observation.setTimestamp(unixTimestampConverter.unixToTimestamp(observations.getTimestamp()));
                    weatherRepository.save(observation);
                }
            }
        }
        catch (JAXBException e){
            log.error("Failed to save weather data due to xml parsing error.", e);
        }
    }

    /**
     * Retrieves weather data from the database for a specified city at a specified time.
     * This method looks up the database for a station observation that matches the given city name and time.
     *
     * @param specifiedTime The LocalDateTime for which the weather data is requested.
     * @param city The city enum for which the weather data is requested.
     * @return Station object containing weather data for the specified city and time, or null if no data is found.
     */
    public Station weatherFromDatabase(LocalDateTime specifiedTime, City city){
        return weatherRepository.findStationByNameAndTime(Timestamp.valueOf(specifiedTime), stationName(city));
    }

    private String stationName(City city){
        return switch(city){
            case TALLINN -> "Tallinn-Harku";
            case TARTU -> "Tartu-Tõravere";
            case PÄRNU -> "Pärnu";
        };
    }
}
