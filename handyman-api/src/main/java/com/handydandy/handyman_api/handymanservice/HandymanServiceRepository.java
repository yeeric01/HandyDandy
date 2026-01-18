package com.handydandy.handyman_api.handymanservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HandymanServiceRepository extends JpaRepository<HandymanService, UUID> {

    List<HandymanService> findByHandymanId(UUID handymanId);

    Page<HandymanService> findByHandymanId(UUID handymanId, Pageable pageable);

    Page<HandymanService> findByCategoryId(UUID categoryId, Pageable pageable);

    Page<HandymanService> findByActiveTrue(Pageable pageable);
}
