package com.handydandy.handyman_api.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByHandymanId(UUID handymanId);

    List<Availability> findByHandymanIdAndAvailableTrue(UUID handymanId);

    Optional<Availability> findByHandymanIdAndDayOfWeek(UUID handymanId, DayOfWeek dayOfWeek);
}
