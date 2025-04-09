package com.RaccoonGG.test.service.model;

import com.RaccoonGG.test.dto.ModelDto;
import com.RaccoonGG.test.dto.ModelPriceUpdateDto;
import com.RaccoonGG.test.mapper.ModelMapper;
import com.RaccoonGG.test.model.Brand;
import com.RaccoonGG.test.model.Model;
import com.RaccoonGG.test.repository.BrandRepository;
import com.RaccoonGG.test.repository.ModelRepository;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl extends GenericServiceImpl<Model, ModelDto, ModelMapper, ModelRepository> implements ModelService {

    private final BrandRepository brandRepository;
    @Autowired
    public ModelServiceImpl(ModelRepository repository, ModelMapper mapper, BrandRepository brandRepository) {
        super(repository, mapper);
        this.brandRepository = brandRepository;
    }

    @Override
    public ResponseEntity<GenericResponse<ModelDto>> save(ModelDto entity) {
        // Check if brand exists
        GenericResponse<ModelDto> response = new GenericResponse<>();
        Long brandId = entity.getBrandId();
        if (!repository.existsById(brandId)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(Boolean.FALSE);
            response.setMessage("Brand not found with id: " + brandId);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Check if model name is unique for this brand
        if (repository.existsByNameAndBrandId(entity.getName(), brandId)) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setSuccess(Boolean.FALSE);
            response.setMessage("Model with name '" + entity.getName() + "' already exists for this brand");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Validate average price
        if (entity.getAveragePrice() != null && entity.getAveragePrice().compareTo(BigDecimal.valueOf(100000)) <= 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(Boolean.FALSE);
            response.setMessage("Average price must be greater than 100,000");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return super.save(entity);
    }

    @Override
    public ResponseEntity<GenericResponse<ModelDto>> updateModelPrice(Long id, ModelPriceUpdateDto priceUpdateDto) {
        GenericResponse<ModelDto> response = new GenericResponse<>();

        // Validate price
        if (priceUpdateDto.getAveragePrice().compareTo(BigDecimal.valueOf(100000)) <= 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(false);
            response.setMessage("Average price must be greater than 100,000");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Find and update model
        return repository.findById(id)
                .map(model -> {
                    model.setAveragePrice(priceUpdateDto.getAveragePrice());
                    Model updatedModel = repository.save(model);

                    // Update brand average price
                    updateBrandAveragePrice(model.getBrand().getId());

                    response.setStatus(HttpStatus.OK.value());
                    response.setSuccess(true);
                    response.setMessage("Model price updated successfully");
                    response.setData(mapper.toDTO(updatedModel));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setSuccess(false);
                    response.setMessage("Model not found with id: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    private void updateBrandAveragePrice(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);
        if (brand != null) {
            BigDecimal averagePrice = repository.findByBrandId(brandId)
                    .stream()
                    .map(Model::getAveragePrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(
                            new BigDecimal(repository.countByBrandId(brandId)),
                            2,
                            RoundingMode.HALF_UP
                    );

            brand.setAveragePrice(averagePrice);
            brandRepository.save(brand);
        }
    }
    @Override
    public ResponseEntity<GenericResponse<List<ModelDto>>> getModelsByPriceRange(
            BigDecimal greater, BigDecimal lower) {
        GenericResponse<List<ModelDto>> response = new GenericResponse<>();

        List<Model> models;
        if (greater != null && lower != null) {
            models = repository.findByAveragePriceBetween(greater, lower);
        } else if (greater != null) {
            models = repository.findByAveragePriceGreaterThan(greater);
        } else if (lower != null) {
            models = repository.findByAveragePriceLessThan(lower);
        } else {
            models = repository.findAll();
        }

        if (models.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setSuccess(false);
            response.setMessage("No models found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage(models.size() + " models found");
        response.setData(models.stream().map(mapper::toDTO).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

}
