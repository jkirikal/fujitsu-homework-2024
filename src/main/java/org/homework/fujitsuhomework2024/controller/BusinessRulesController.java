package org.homework.fujitsuhomework2024.controller;

import org.homework.fujitsuhomework2024.enums.City;
import org.homework.fujitsuhomework2024.enums.VehicleType;
import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.BusinessRulesDto;
import org.homework.fujitsuhomework2024.service.BusinessRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles web requests related to business rules.
 * Provides endpoints for creating, retrieving, updating, and deleting business rules.
 */
@RestController
public class BusinessRulesController {
    private final BusinessRulesService businessRulesService;

    public BusinessRulesController(BusinessRulesService businessRulesService) {
        this.businessRulesService = businessRulesService;
    }

    /**
     * Creates a new business rule from the provided DTO.
     * @param newRule The DTO containing the data for the new business rule.
     * @return The created BusinessRules entity.
     */
    @PostMapping("/api/rules")
    public BusinessRules createBusinessRule(@RequestBody BusinessRulesDto newRule) {
        return businessRulesService.createBusinessRule(newRule);
    }

    /**
     * Retrieves a business rule by its ID.
     * @param id The ID of the business rule to retrieve.
     * @return A ResponseEntity containing the requested BusinessRules entity, or NotFound if it does not exist.
     */
    @GetMapping("/api/rules/{id}")
    public ResponseEntity<BusinessRules> getBusinessRule(@PathVariable Long id) {
        return businessRulesService.getBusinessRule(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing business rule with the provided ID and DTO.
     * @param id The ID of the business rule to update.
     * @param updatedRule The DTO containing the updated data for the business rule.
     * @return A ResponseEntity containing the updated BusinessRules entity, or NotFound if the entity with the provided ID does not exist.
     */
    @PutMapping("/api/rules/{id}")
    public ResponseEntity<BusinessRules> updateBusinessRule(@PathVariable Long id, @RequestBody BusinessRulesDto updatedRule) {
        return businessRulesService.updateBusinessRule(id, updatedRule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a business rule by its ID.
     * @param id The ID of the business rule to delete.
     */
    @DeleteMapping("/api/rules/{id}")
    public void deleteBusinessRule(@PathVariable Long id) {
        businessRulesService.deleteBusinessRule(id);
    }

    /**
     * Retrieves all business rules.
     * @return A list of all BusinessRules entities.
     */
    @GetMapping("/api/rules")
    public List<BusinessRules> getAllBusinessRules() {
        return businessRulesService.getAllBusinessRules();
    }
}
