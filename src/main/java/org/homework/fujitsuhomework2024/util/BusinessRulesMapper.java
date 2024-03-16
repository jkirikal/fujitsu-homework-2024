package org.homework.fujitsuhomework2024.util;

import org.homework.fujitsuhomework2024.model.BusinessRules;
import org.homework.fujitsuhomework2024.model.BusinessRulesDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BusinessRulesMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBusinessRulesFromDto(BusinessRulesDto dto, @MappingTarget BusinessRules entity);
}
