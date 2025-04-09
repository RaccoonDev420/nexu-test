package com.RaccoonGG.test.controller;

import com.RaccoonGG.test.dto.ModelDto;
import com.RaccoonGG.test.dto.ModelPriceUpdateDto;
import com.RaccoonGG.test.model.Model;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.model.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/model")
public class ModelController extends GenericController<Model, ModelDto, ModelService>{
    @Autowired
    public ModelController(ModelService service) {
        super(service);
    }

    @Override
    public ResponseEntity<GenericResponse<List<ModelDto>>> findAll() {
        return super.findAll();
    }

    @Override
    public ResponseEntity<GenericResponse<ModelDto>> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public ResponseEntity<GenericResponse<ModelDto>> save(ModelDto entity) {
        return super.save(entity);
    }

    @PostMapping("/brands/{brandId}")
    public ResponseEntity<GenericResponse<ModelDto>> createModel(
            @PathVariable Long brandId,
            @Valid @RequestBody ModelDto modelDto) {
        modelDto.setBrandId(brandId);
        return service.save(modelDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ModelDto>> updateModelPrice(
            @PathVariable Long id,
            @Valid @RequestBody ModelPriceUpdateDto priceUpdateDto) {
        return service.updateModelPrice(id, priceUpdateDto);
    }

    @GetMapping("/by-price")
    public ResponseEntity<GenericResponse<List<ModelDto>>> getModelsByPriceRange(
            @RequestParam(required = false) BigDecimal greater,
            @RequestParam(required = false) BigDecimal lower) {
        return service.getModelsByPriceRange(greater, lower);
    }
}
