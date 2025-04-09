package com.RaccoonGG.test.repository;

import com.RaccoonGG.test.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    Boolean existsByName(String name);

}
