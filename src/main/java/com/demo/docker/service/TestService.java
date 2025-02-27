package com.demo.docker.service;

import com.demo.docker.entity.TestEntity;
import com.demo.docker.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public TestEntity save(TestEntity testEntity) {
        return testRepository.save(testEntity);
    }

    public TestEntity findById(UUID id) {
        return testRepository.findById(id).orElse(null);
    }

    public List<TestEntity> findAll() {
        return testRepository.findAll();
    }

    public void delete(UUID id) {
        testRepository.deleteById(id);
    }

}
