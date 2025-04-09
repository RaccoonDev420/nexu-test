package com.RaccoonGG.test.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;

@MapperConfig(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IgnoreNullMapperConfig {
}
