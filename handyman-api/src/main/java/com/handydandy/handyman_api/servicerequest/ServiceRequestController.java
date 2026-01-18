package com.handydandy.handyman_api.servicerequest;

import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestCreateRequest;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestResponse;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping
    public ResponseEntity<Page<ServiceRequestResponse>> getAllRequests(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(serviceRequestService.getAllRequests(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> getRequestById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceRequestService.getRequestById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<ServiceRequestResponse>> getRequestsByCustomer(
            @PathVariable UUID customerId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(serviceRequestService.getRequestsByCustomer(customerId, pageable));
    }

    @GetMapping("/handyman/{handymanId}")
    public ResponseEntity<Page<ServiceRequestResponse>> getRequestsByHandyman(
            @PathVariable UUID handymanId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(serviceRequestService.getRequestsByHandyman(handymanId, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ServiceRequestResponse>> getRequestsByStatus(
            @PathVariable String status,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(serviceRequestService.getRequestsByStatus(status, pageable));
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponse> createRequest(
            @Valid @RequestBody ServiceRequestCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(serviceRequestService.createRequest(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> updateRequest(
            @PathVariable UUID id,
            @Valid @RequestBody ServiceRequestUpdateRequest request) {
        return ResponseEntity.ok(serviceRequestService.updateRequest(id, request));
    }

    @PostMapping("/{id}/assign/{handymanId}")
    public ResponseEntity<ServiceRequestResponse> assignHandyman(
            @PathVariable UUID id,
            @PathVariable UUID handymanId) {
        return ResponseEntity.ok(serviceRequestService.assignHandyman(id, handymanId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID id) {
        serviceRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
