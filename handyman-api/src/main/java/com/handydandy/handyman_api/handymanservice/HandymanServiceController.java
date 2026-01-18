package com.handydandy.handyman_api.handymanservice;

import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceCreateRequest;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceResponse;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/handyman-services")
public class HandymanServiceController {

    private final HandymanServiceService handymanServiceService;

    public HandymanServiceController(HandymanServiceService handymanServiceService) {
        this.handymanServiceService = handymanServiceService;
    }

    @GetMapping
    public ResponseEntity<Page<HandymanServiceResponse>> getAllServices(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(handymanServiceService.getAllServices(pageable));
    }

    @GetMapping("/handyman/{handymanId}")
    public ResponseEntity<Page<HandymanServiceResponse>> getServicesByHandyman(
            @PathVariable UUID handymanId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(handymanServiceService.getServicesByHandyman(handymanId, pageable));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<HandymanServiceResponse>> getServicesByCategory(
            @PathVariable UUID categoryId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(handymanServiceService.getServicesByCategory(categoryId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HandymanServiceResponse> getServiceById(@PathVariable UUID id) {
        return ResponseEntity.ok(handymanServiceService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<HandymanServiceResponse> createService(
            @Valid @RequestBody HandymanServiceCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(handymanServiceService.createService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HandymanServiceResponse> updateService(
            @PathVariable UUID id,
            @Valid @RequestBody HandymanServiceUpdateRequest request) {
        return ResponseEntity.ok(handymanServiceService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        handymanServiceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
