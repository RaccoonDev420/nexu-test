package com.RaccoonGG.test.service;

import com.RaccoonGG.test.dto.GenericDto;
import com.RaccoonGG.test.response.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericService<Entity, Dto extends GenericDto>{
    ResponseEntity<GenericResponse<Dto>> findById(Long id);
    ResponseEntity<GenericResponse<Dto>> save(Dto entity);
    ResponseEntity<GenericResponse<List<Dto>>> findAll();
    void delete(Long id);
}
