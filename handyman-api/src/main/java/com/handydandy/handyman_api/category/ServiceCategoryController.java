package com.handydandy.handyman_api.category;

import com.handydandy.handyman_api.category.dto.ServiceCategoryCreateRequest;
import com.handydandy.handyman_api.category.dto.ServiceCategoryResponse;
import com.handydandy.handyman_api.category.dto.ServiceCategoryUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class ServiceCategoryController {

    private final ServiceCategoryService categoryService;

    public ServiceCategoryController(ServiceCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<ServiceCategoryResponse>> getAllCategories(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ServiceCategoryResponse>> getActiveCategories(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(categoryService.getActiveCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponse> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceCategoryResponse> createCategory(
            @Valid @RequestBody ServiceCategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponse> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody ServiceCategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
