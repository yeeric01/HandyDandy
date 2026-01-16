package com.handydandy.handyman_api.servicerequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    // Get all requests for a customer
    List<ServiceRequest> findByCustomerId(UUID customerId);

    // Get all requests for a handyman
    List<ServiceRequest> findByHandymanId(UUID handymanId);

    // Optional: find requests by status
    List<ServiceRequest> findByStatus(ServiceRequest.Status status);
}
