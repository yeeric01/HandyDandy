package com.handydandy.handyman_api.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Page<Booking> findByCustomerId(UUID customerId, Pageable pageable);

    Page<Booking> findByHandymanId(UUID handymanId, Pageable pageable);

    Page<Booking> findByStatus(Booking.Status status, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.handyman.id = :handymanId " +
           "AND b.scheduledStart >= :start AND b.scheduledEnd <= :endTime")
    List<Booking> findByHandymanAndTimeRange(
        @Param("handymanId") UUID handymanId,
        @Param("start") LocalDateTime start,
        @Param("endTime") LocalDateTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.handyman.id = :handymanId " +
           "AND b.status IN ('PENDING', 'CONFIRMED', 'IN_PROGRESS')")
    List<Booking> findActiveBookingsForHandyman(@Param("handymanId") UUID handymanId);
}
