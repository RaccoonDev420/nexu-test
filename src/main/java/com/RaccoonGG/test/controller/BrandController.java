package com.RaccoonGG.test.controller;

import com.RaccoonGG.test.dto.BrandDto;
import com.RaccoonGG.test.model.Brand;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController extends GenericController<Brand, BrandDto, BrandService>{
    @Autowired
    public BrandController(BrandService service) {
        super(service);
    }

    @Override
    public ResponseEntity<GenericResponse<List<BrandDto>>> findAll() {
        return super.findAll();
    }

    @Override
    public ResponseEntity<GenericResponse<BrandDto>> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public ResponseEntity<GenericResponse<BrandDto>> save(BrandDto entity) {
        return super.save(entity);
    }
}
