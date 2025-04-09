package com.RaccoonGG.test.service;

import com.RaccoonGG.test.dto.GenericDto;
import com.RaccoonGG.test.mapper.GenericMapper;
import com.RaccoonGG.test.response.GenericResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class GenericServiceImpl<E, Dto extends GenericDto, Mapper extends GenericMapper<E, Dto>, R extends JpaRepository<E,Long>> implements GenericService<E, Dto> {

    protected final R repository;
    protected final Mapper mapper;

    public GenericServiceImpl(R repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public ResponseEntity<GenericResponse<Dto>> findById(Long id) {
        Optional<E> result = repository.findById(id);
        GenericResponse<Dto> response = new GenericResponse<>();
        if (result.isPresent()){
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Entity created successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(mapper.toDTO(result.get()));
            return ResponseEntity.ok(response);
        }
        response.setSuccess(Boolean.FALSE);
        response.setMessage("Couldn't create entity");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(null);
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public ResponseEntity<GenericResponse<Dto>> save(Dto entity) {
        entity.setId(null);
        GenericResponse<Dto> response = new GenericResponse<>();
        response.setMessage("Entity created Successfully");
        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(Boolean.TRUE);
        response.setData(mapper.toDTO(repository.save(mapper.toEntity(entity))));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponse<List<Dto>>> findAll() {
        GenericResponse<List<Dto>> response = new GenericResponse<>();
        List<E> result = repository.findAll();
        if (result.isEmpty()){
            response.setData(null);
            response.setSuccess(Boolean.FALSE);
            response.setMessage("No Entities found");
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        response.setData(result.stream().map(mapper::toDTO).collect(Collectors.toList()));
        response.setSuccess(Boolean.TRUE);
        response.setMessage(result.size() + " Entities found");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
