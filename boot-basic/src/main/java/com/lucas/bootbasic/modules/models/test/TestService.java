package com.lucas.bootbasic.modules.models.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository repository;


    @Transactional
    public void save(TestEntity entity) {
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<TestEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TestEntity findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(readOnly = true)
    public TestEntity findByValue(String value) {
        return repository.findByValue(value).orElseThrow(IllegalArgumentException::new);
    }

    // -------------------------------------------- CUSTOM -----------------------------------------------------

    @Transactional(readOnly = true)
    public List<TestDTO> findAllWithDto() {
//        List<TestEntity> entities = repository.findAll();
//        List<TestDTO> list = entities.stream()
//                .map(TestDTO::fromEntity)
//                .toList();
        // refactor
        return repository.findAll().stream()
                .map(TestDTO::fromEntity)
                .collect(Collectors.toList());

    }

    // Response DTO Return
    @Transactional(readOnly = true)
    public TestAllDTO.Response resDTOTest(Long id) {
        TestEntity entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("id is not found"));
        TestAllDTO.Response dto = TestAllDTO.Response.fromEntity(entity);
        return dto;
    }

    // Request DTO To Save
    @Transactional
    public TestEntity dtoToSaveEntity(TestAllDTO.Request dto) {
        TestEntity entity = repository.save(dto.toEntity());
        return entity;
    }

}
