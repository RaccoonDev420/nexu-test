package com.RaccoonGG.test.service.brand;

import com.RaccoonGG.test.dto.BrandDto;
import com.RaccoonGG.test.mapper.BrandMapper;
import com.RaccoonGG.test.model.Brand;
import com.RaccoonGG.test.repository.BrandRepository;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class BrandServiceImpl extends GenericServiceImpl<Brand, BrandDto, BrandMapper, BrandRepository> implements BrandService{
    @Autowired
    public BrandServiceImpl(BrandRepository repository, BrandMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ResponseEntity<GenericResponse<BrandDto>> save(BrandDto entity) {
        if (repository.existsByName(entity.getName())){
            GenericResponse<BrandDto> response = new GenericResponse<>();
            response.setData(null);
            response.setSuccess(Boolean.FALSE);
            response.setMessage("Brand with name: '" + entity.getName() + "' already exists");
            response.setStatus(HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return super.save(entity);
    }



}
