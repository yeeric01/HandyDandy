package com.handydandy.handyman_api.quote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    Page<Quote> findByHandymanId(UUID handymanId, Pageable pageable);

    Page<Quote> findByCustomerId(UUID customerId, Pageable pageable);

    Page<Quote> findByServiceRequestId(UUID serviceRequestId, Pageable pageable);

    Page<Quote> findByStatus(Quote.Status status, Pageable pageable);
}
