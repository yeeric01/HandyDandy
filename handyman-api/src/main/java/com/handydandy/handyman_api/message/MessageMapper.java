package com.handydandy.handyman_api.message;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.message.dto.MessageCreateRequest;
import com.handydandy.handyman_api.message.dto.MessageResponse;
import com.handydandy.handyman_api.profile.Profile;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toEntity(MessageCreateRequest request, Profile sender, Profile receiver, Booking booking) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setBooking(booking);
        message.setContent(request.content());
        return message;
    }

    public MessageResponse toResponse(Message message) {
        MessageResponse.ProfileSummary senderSummary = null;
        if (message.getSender() != null) {
            Profile s = message.getSender();
            senderSummary = new MessageResponse.ProfileSummary(s.getId(), s.getName(), s.getEmail());
        }

        MessageResponse.ProfileSummary receiverSummary = null;
        if (message.getReceiver() != null) {
            Profile r = message.getReceiver();
            receiverSummary = new MessageResponse.ProfileSummary(r.getId(), r.getName(), r.getEmail());
        }

        return new MessageResponse(
            message.getId(),
            senderSummary,
            receiverSummary,
            message.getBooking() != null ? message.getBooking().getId() : null,
            message.getContent(),
            message.getSentAt(),
            message.getReadAt(),
            message.isDeleted()
        );
    }
}
