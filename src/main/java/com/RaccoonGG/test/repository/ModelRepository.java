package com.RaccoonGG.test.repository;

import com.RaccoonGG.test.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByBrandId(Long brandId);
    Boolean existsByNameAndBrandId(String name, Long brandId);
    long countByBrandId(Long brandId);
    List<Model> findByAveragePriceBetween(BigDecimal lower, BigDecimal upper);
    List<Model> findByAveragePriceGreaterThan(BigDecimal price);
    List<Model> findByAveragePriceLessThan(BigDecimal price);
}
