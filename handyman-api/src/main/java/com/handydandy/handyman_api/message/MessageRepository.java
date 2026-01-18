package com.handydandy.handyman_api.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findBySenderIdAndDeletedFalse(UUID senderId, Pageable pageable);

    Page<Message> findByReceiverIdAndDeletedFalse(UUID receiverId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.deleted = false " +
           "AND ((m.sender.id = :user1 AND m.receiver.id = :user2) " +
           "OR (m.sender.id = :user2 AND m.receiver.id = :user1)) " +
           "ORDER BY m.sentAt DESC")
    Page<Message> findConversation(
        @Param("user1") UUID user1,
        @Param("user2") UUID user2,
        Pageable pageable);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.receiver.id = :userId " +
           "AND m.readAt IS NULL AND m.deleted = false")
    Long countUnreadMessages(@Param("userId") UUID userId);

    Page<Message> findByBookingIdAndDeletedFalse(UUID bookingId, Pageable pageable);
}
