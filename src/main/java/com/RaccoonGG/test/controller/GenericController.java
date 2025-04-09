package com.RaccoonGG.test.controller;

import com.RaccoonGG.test.dto.GenericDto;
import com.RaccoonGG.test.response.GenericResponse;
import com.RaccoonGG.test.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public class GenericController<Entity, Dto extends GenericDto, Service extends GenericService<Entity, Dto>> {

    protected final Service service;

    public GenericController(Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<Dto>>> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<Dto>> findById(@PathVariable() Long id){
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<GenericResponse<Dto>> save(@RequestBody Dto entity){
        return service.save(entity);
    }

}
