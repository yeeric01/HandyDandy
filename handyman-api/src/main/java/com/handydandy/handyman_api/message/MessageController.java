package com.handydandy.handyman_api.message;

import com.handydandy.handyman_api.message.dto.MessageCreateRequest;
import com.handydandy.handyman_api.message.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/conversation")
    public ResponseEntity<Page<MessageResponse>> getConversation(
            @RequestParam UUID user1,
            @RequestParam UUID user2,
            @PageableDefault(size = 50, sort = "sentAt") Pageable pageable) {
        return ResponseEntity.ok(messageService.getConversation(user1, user2, pageable));
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<Page<MessageResponse>> getMessagesBySender(
            @PathVariable UUID senderId,
            @PageableDefault(size = 20, sort = "sentAt") Pageable pageable) {
        return ResponseEntity.ok(messageService.getMessagesBySender(senderId, pageable));
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<Page<MessageResponse>> getMessagesByReceiver(
            @PathVariable UUID receiverId,
            @PageableDefault(size = 20, sort = "sentAt") Pageable pageable) {
        return ResponseEntity.ok(messageService.getMessagesByReceiver(receiverId, pageable));
    }

    @GetMapping("/unread/{userId}/count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getUnreadCount(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessageById(@PathVariable UUID id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @Valid @RequestBody MessageCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(messageService.sendMessage(request));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<MessageResponse> markAsRead(@PathVariable UUID id) {
        return ResponseEntity.ok(messageService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
