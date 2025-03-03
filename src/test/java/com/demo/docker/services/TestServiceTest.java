package com.demo.docker.services;

import com.demo.docker.entity.TestEntity;
import com.demo.docker.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestService testService;

    private TestEntity testEntity;
    private UUID testId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testId = UUID.randomUUID();
        testEntity = new TestEntity();
        testEntity.setId(testId);
    }

    @Test
    void saveEntitySuccessfully() {
        when(testRepository.save(testEntity)).thenReturn(testEntity);
        TestEntity savedEntity = testService.save(testEntity);
        assertEquals(testEntity, savedEntity);
        verify(testRepository, times(1)).save(testEntity);
    }

    @Test
    void findEntityByIdSuccessfully() {
        when(testRepository.findById(testId)).thenReturn(Optional.of(testEntity));
        TestEntity foundEntity = testService.findById(testId);
        assertEquals(testEntity, foundEntity);
        verify(testRepository, times(1)).findById(testId);
    }

    @Test
    void findEntityByIdThrowsExceptionWhenNotFound() {
        when(testRepository.findById(testId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> testService.findById(testId));
        verify(testRepository, times(1)).findById(testId);
    }

    @Test
    void findAllEntitiesSuccessfully() {
        List<TestEntity> entities = List.of(testEntity);
        when(testRepository.findAll()).thenReturn(entities);
        List<TestEntity> foundEntities = testService.findAll();
        assertEquals(entities, foundEntities);
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void deleteEntitySuccessfully() {
        doNothing().when(testRepository).deleteById(testId);
        testService.delete(testId);
        verify(testRepository, times(1)).deleteById(testId);
    }
}
