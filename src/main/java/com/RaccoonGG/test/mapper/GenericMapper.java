package com.RaccoonGG.test.mapper;

import org.springframework.stereotype.Component;

@Component
public interface GenericMapper <E,DTO>{
    E toEntity(DTO dto);
    DTO toDTO(E entity);
}
