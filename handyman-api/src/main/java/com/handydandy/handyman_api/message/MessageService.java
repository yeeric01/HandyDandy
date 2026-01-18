package com.handydandy.handyman_api.message;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.booking.BookingRepository;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.message.dto.MessageCreateRequest;
import com.handydandy.handyman_api.message.dto.MessageResponse;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;
    private final BookingRepository bookingRepository;
    private final MessageMapper mapper;

    public MessageService(MessageRepository messageRepository,
                          ProfileRepository profileRepository,
                          BookingRepository bookingRepository,
                          MessageMapper mapper) {
        this.messageRepository = messageRepository;
        this.profileRepository = profileRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    public MessageResponse sendMessage(MessageCreateRequest request) {
        Profile sender = profileRepository.findById(request.senderId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.senderId()));

        Profile receiver = profileRepository.findById(request.receiverId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.receiverId()));

        Booking booking = null;
        if (request.bookingId() != null) {
            booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", request.bookingId()));
        }

        Message message = mapper.toEntity(request, sender, receiver, booking);
        return mapper.toResponse(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> getConversation(UUID user1, UUID user2, Pageable pageable) {
        return messageRepository.findConversation(user1, user2, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessagesBySender(UUID senderId, Pageable pageable) {
        return messageRepository.findBySenderIdAndDeletedFalse(senderId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessagesByReceiver(UUID receiverId, Pageable pageable) {
        return messageRepository.findByReceiverIdAndDeletedFalse(receiverId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Long getUnreadCount(UUID userId) {
        return messageRepository.countUnreadMessages(userId);
    }

    @Transactional(readOnly = true)
    public MessageResponse getMessageById(UUID id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        return mapper.toResponse(message);
    }

    public MessageResponse markAsRead(UUID id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        message.setReadAt(LocalDateTime.now());
        return mapper.toResponse(messageRepository.save(message));
    }

    public void deleteMessage(UUID id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        message.setDeleted(true);
        messageRepository.save(message);
    }
}
