package com.demo.docker.controllers;

import com.demo.docker.entity.TestEntity;
import com.demo.docker.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/")
    public ResponseEntity<TestEntity> save(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(testService.save(testEntity));
    }

    @GetMapping("/")
    public ResponseEntity<List<TestEntity>> getAllTest() {
        return ResponseEntity.ok(testService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestEntity> getTestById(@PathVariable UUID id) {
        return ResponseEntity.ok(testService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable UUID id) {
        testService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
