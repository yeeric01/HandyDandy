package com.handydandy.handyman_api.quote;

import com.handydandy.handyman_api.quote.dto.QuoteCreateRequest;
import com.handydandy.handyman_api.quote.dto.QuoteRespondRequest;
import com.handydandy.handyman_api.quote.dto.QuoteResponse;
import com.handydandy.handyman_api.quote.dto.QuoteUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<Page<QuoteResponse>> getAllQuotes(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(quoteService.getAllQuotes(pageable));
    }

    @GetMapping("/handyman/{handymanId}")
    public ResponseEntity<Page<QuoteResponse>> getQuotesByHandyman(
            @PathVariable UUID handymanId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(quoteService.getQuotesByHandyman(handymanId, pageable));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<QuoteResponse>> getQuotesByCustomer(
            @PathVariable UUID customerId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(quoteService.getQuotesByCustomer(customerId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuoteById(@PathVariable UUID id) {
        return ResponseEntity.ok(quoteService.getQuoteById(id));
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> createQuote(
            @Valid @RequestBody QuoteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(quoteService.createQuote(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponse> updateQuote(
            @PathVariable UUID id,
            @Valid @RequestBody QuoteUpdateRequest request) {
        return ResponseEntity.ok(quoteService.updateQuote(id, request));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<QuoteResponse> respondToQuote(
            @PathVariable UUID id,
            @Valid @RequestBody QuoteRespondRequest request) {
        return ResponseEntity.ok(quoteService.respondToQuote(id, request.action()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable UUID id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
}
