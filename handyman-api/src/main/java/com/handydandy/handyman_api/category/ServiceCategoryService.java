package com.handydandy.handyman_api.category;

import com.handydandy.handyman_api.category.dto.ServiceCategoryCreateRequest;
import com.handydandy.handyman_api.category.dto.ServiceCategoryResponse;
import com.handydandy.handyman_api.category.dto.ServiceCategoryUpdateRequest;
import com.handydandy.handyman_api.exception.DuplicateResourceException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;
    private final ServiceCategoryMapper mapper;

    public ServiceCategoryService(ServiceCategoryRepository categoryRepository, ServiceCategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public ServiceCategoryResponse createCategory(ServiceCategoryCreateRequest request) {
        if (categoryRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Category with name '" + request.name() + "' already exists");
        }
        ServiceCategory category = mapper.toEntity(request);
        return mapper.toResponse(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Page<ServiceCategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ServiceCategoryResponse> getActiveCategories(Pageable pageable) {
        return categoryRepository.findByActiveTrue(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ServiceCategoryResponse getCategoryById(UUID id) {
        ServiceCategory category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", id));
        return mapper.toResponse(category);
    }

    public ServiceCategoryResponse updateCategory(UUID id, ServiceCategoryUpdateRequest request) {
        ServiceCategory category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", id));
        mapper.updateEntity(category, request);
        return mapper.toResponse(categoryRepository.save(category));
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("ServiceCategory", "id", id);
        }
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ServiceCategory getEntityById(UUID id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", id));
    }
}
