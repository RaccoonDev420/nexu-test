package com.RaccoonGG.test.mapper;

import com.RaccoonGG.test.dto.BrandDto;
import com.RaccoonGG.test.model.Brand;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        config = IgnoreNullMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface BrandMapper extends GenericMapper<Brand, BrandDto> {
}
