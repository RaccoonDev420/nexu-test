package com.RaccoonGG.test.service.model;

import com.RaccoonGG.test.dto.ModelDto;
import com.RaccoonGG.test.dto.ModelPriceUpdateDto;
import com.RaccoonGG.test.model.Model;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.GenericService;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ModelService extends GenericService<Model, ModelDto> {
    ResponseEntity<GenericResponse<ModelDto>> updateModelPrice(Long id, ModelPriceUpdateDto priceUpdateDto);
    ResponseEntity<GenericResponse<List<ModelDto>>> getModelsByPriceRange(BigDecimal greater, BigDecimal lower);
}
