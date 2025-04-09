package com.RaccoonGG.test.service;

import com.RaccoonGG.test.model.Brand;
import com.RaccoonGG.test.model.Model;
import com.RaccoonGG.test.repository.BrandRepository;
import com.RaccoonGG.test.repository.ModelRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataInitializationService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final ObjectMapper objectMapper;

    public DataInitializationService(BrandRepository brandRepository,
                                     ModelRepository modelRepository,
                                     ObjectMapper objectMapper) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    @Transactional
    public void initializeData() {
        if (shouldInitializeData()) {
            try {
                List<CarModelData> carModels = loadCarModelsFromJson();
                Map<String, Long> brandNameToIdMap = saveBrands(carModels);
                saveModels(carModels, brandNameToIdMap);
                updateBrandAveragePrices(); // New method to update brand averages
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize car data", e);
            }
        }
    }

    private boolean shouldInitializeData() {
        return brandRepository.count() == 0 && modelRepository.count() == 0;
    }

    private List<CarModelData> loadCarModelsFromJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/models.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        }
    }

    private Map<String, Long> saveBrands(List<CarModelData> carModels) {
        // Get unique brand names
        Set<String> brandNames = carModels.stream()
                .map(CarModelData::getBrandName)
                .collect(Collectors.toSet());

        // Save brands and create mapping
        Map<String, Long> brandNameToIdMap = new HashMap<>();
        brandNames.forEach(brandName -> {
            Brand brand = new Brand();
            brand.setName(brandName);
            Brand savedBrand = brandRepository.save(brand);
            brandNameToIdMap.put(brandName, savedBrand.getId());
        });

        return brandNameToIdMap;
    }

    private void saveModels(List<CarModelData> carModels, Map<String, Long> brandNameToIdMap) {
        carModels.forEach(carModel -> {
            Model model = new Model();
            model.setName(carModel.getName());
            model.setAveragePrice(carModel.getAveragePrice());

            Brand brand = new Brand();
            brand.setId(brandNameToIdMap.get(carModel.getBrandName()));
            model.setBrand(brand);

            modelRepository.save(model);
        });
    }

    private void updateBrandAveragePrices() {
        List<Brand> allBrands = brandRepository.findAll();

        allBrands.forEach(brand -> {
            // Calculate average price for the brand
            BigDecimal averagePrice = modelRepository.findByBrandId(brand.getId())
                    .stream()
                    .map(Model::getAveragePrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(
                            new BigDecimal(modelRepository.countByBrandId(brand.getId())),
                            2,
                            RoundingMode.HALF_UP
                    );

            // Update the brand
            brand.setAveragePrice(averagePrice);
            brandRepository.save(brand);
        });
    }

    private static class CarModelData {
        private Long id;
        private String name;

        @JsonProperty("average_price")
        private BigDecimal averagePrice;

        @JsonProperty("brand_name")
        private String brandName;

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public BigDecimal getAveragePrice() { return averagePrice; }
        public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }
        public String getBrandName() { return brandName; }
        public void setBrandName(String brandName) { this.brandName = brandName; }
    }
}
