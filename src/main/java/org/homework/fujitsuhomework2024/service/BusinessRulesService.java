package org.homework.fujitsuhomework2024.service;

import jakarta.annotation.PostConstruct;
import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.BusinessRulesDto;
import org.homework.fujitsuhomework2024.repository.BusinessRulesRepository;
import org.homework.fujitsuhomework2024.util.BusinessRulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessRulesService {

    private final BusinessRulesRepository businessRulesRepository;
    private final BusinessRulesMapper businessRulesMapper;

    @Autowired
    public BusinessRulesService(BusinessRulesRepository businessRulesRepository, BusinessRulesMapper businessRulesMapper) {
        this.businessRulesRepository = businessRulesRepository;
        this.businessRulesMapper = businessRulesMapper;
    }

    public Optional<BusinessRules> getBusinessRule(Long id) {
        return businessRulesRepository.findById(id);
    }

    /**
     * Creates a new BusinessRules entity from a DTO and saves it to the repository.
     *
     * @param dto The BusinessRulesDto containing the data to create the new BusinessRules entity.
     * @return The newly created and saved BusinessRules entity.
     */
    public BusinessRules createBusinessRule(BusinessRulesDto dto){
        BusinessRules rules = new BusinessRules();
        businessRulesMapper.updateBusinessRulesFromDto(dto, rules);

        Instant now = Instant.now();
        Timestamp timestamp = Timestamp.from(now);
        rules.setTimestamp(timestamp);
        return businessRulesRepository.save(rules);
    }

    /**
     * Updates an existing business ruleset with data from a DTO and saves the changes.
     *
     * @param id The ID of the BusinessRules entity to update.
     * @param updatedRule The DTO containing the updated data.
     * @return An Optional containing the updated BusinessRules entity, or an empty Optional if the entity with the given ID was not found.
     */
    public Optional<BusinessRules> updateBusinessRule(Long id, BusinessRulesDto updatedRule) {
        return businessRulesRepository.findById(id)
            .map(rules -> {
                businessRulesMapper.updateBusinessRulesFromDto(updatedRule, rules);
                return businessRulesRepository.save(rules);
            });
    }

    public void deleteBusinessRule(Long id) {
        businessRulesRepository.deleteById(id);
    }

    public List<BusinessRules> getAllBusinessRules() {
        return businessRulesRepository.findAll();
    }

    /**
     * Retrieves a BusinessRules entity that matches the given timestamp.
     *
     * @param timestamp The timestamp to match.
     * @return The matching BusinessRules entity.
     */
    public BusinessRules BusinessRulesByTimestamp(Timestamp timestamp){
        timestamp.setMinutes(timestamp.getMinutes()+1);
        return businessRulesRepository.findBusinessRulesByTimestamp(timestamp);
    }

    /**
     * Sets initial business rules using a predefined DTO. This method is called automatically after the bean's properties are set.
     */
    @PostConstruct
    public void setInitialRules(){
        BusinessRulesDto dto = new BusinessRulesDto(1, 0.5F, 0, 3, 2.5F, 2, 1, 0.5F, -1, 0.5F, 1, -1, -1);
        BusinessRules rules = new BusinessRules();
        businessRulesMapper.updateBusinessRulesFromDto(dto, rules);

        Instant now = Instant.now();
        Timestamp timestamp = Timestamp.from(now);
        rules.setTimestamp(timestamp);
        businessRulesRepository.save(rules);
    }
}
