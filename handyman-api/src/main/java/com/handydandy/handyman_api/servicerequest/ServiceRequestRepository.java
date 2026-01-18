package com.handydandy.handyman_api.servicerequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    List<ServiceRequest> findByCustomerId(UUID customerId);

    Page<ServiceRequest> findByCustomerId(UUID customerId, Pageable pageable);

    List<ServiceRequest> findByHandymanId(UUID handymanId);

    Page<ServiceRequest> findByHandymanId(UUID handymanId, Pageable pageable);

    List<ServiceRequest> findByStatus(ServiceRequest.Status status);

    Page<ServiceRequest> findByStatus(ServiceRequest.Status status, Pageable pageable);
}
