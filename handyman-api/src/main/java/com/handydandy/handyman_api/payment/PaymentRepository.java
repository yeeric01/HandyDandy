package com.handydandy.handyman_api.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByBookingId(UUID bookingId);

    Page<Payment> findByPayerId(UUID payerId, Pageable pageable);

    Page<Payment> findByPayeeId(UUID payeeId, Pageable pageable);

    Page<Payment> findByStatus(Payment.Status status, Pageable pageable);
}
