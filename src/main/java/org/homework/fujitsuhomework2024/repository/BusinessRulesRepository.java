package org.homework.fujitsuhomework2024.repository;

import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface BusinessRulesRepository extends JpaRepository<BusinessRules, Long> {
    @Query("SELECT b FROM BusinessRules b WHERE b.timestamp = (SELECT MAX(b2.timestamp) FROM BusinessRules b2 WHERE b2.timestamp <= :customTimestamp)")
    BusinessRules findBusinessRulesByTimestamp(@Param("customTimestamp") Timestamp customTimestamp);
}
