package com.RaccoonGG.test.mapper;

import com.RaccoonGG.test.dto.ModelDto;
import com.RaccoonGG.test.model.Model;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        config = IgnoreNullMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface ModelMapper extends GenericMapper<Model, ModelDto> {
    @Override
    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "brandName", target = "brand.name")
    Model toEntity(ModelDto modelDto);

    @Override
    @InheritInverseConfiguration()
    ModelDto toDTO(Model entity);
}
