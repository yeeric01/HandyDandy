package com.handydandy.handyman_api.quote;

import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.category.ServiceCategoryRepository;
import com.handydandy.handyman_api.exception.BadRequestException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import com.handydandy.handyman_api.quote.dto.QuoteCreateRequest;
import com.handydandy.handyman_api.quote.dto.QuoteResponse;
import com.handydandy.handyman_api.quote.dto.QuoteUpdateRequest;
import com.handydandy.handyman_api.servicerequest.ServiceRequest;
import com.handydandy.handyman_api.servicerequest.ServiceRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ProfileRepository profileRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final QuoteMapper mapper;

    public QuoteService(QuoteRepository quoteRepository,
                        ProfileRepository profileRepository,
                        ServiceCategoryRepository categoryRepository,
                        ServiceRequestRepository serviceRequestRepository,
                        QuoteMapper mapper) {
        this.quoteRepository = quoteRepository;
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
    }

    public QuoteResponse createQuote(QuoteCreateRequest request) {
        Profile handyman = profileRepository.findById(request.handymanId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));

        Profile customer = profileRepository.findById(request.customerId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.customerId()));

        ServiceCategory category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", request.categoryId()));

        ServiceRequest serviceRequest = null;
        if (request.serviceRequestId() != null) {
            serviceRequest = serviceRequestRepository.findById(request.serviceRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest", "id", request.serviceRequestId()));
        }

        Quote quote = mapper.toEntity(request, handyman, customer, serviceRequest, category);
        return mapper.toResponse(quoteRepository.save(quote));
    }

    @Transactional(readOnly = true)
    public Page<QuoteResponse> getAllQuotes(Pageable pageable) {
        return quoteRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<QuoteResponse> getQuotesByHandyman(UUID handymanId, Pageable pageable) {
        return quoteRepository.findByHandymanId(handymanId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<QuoteResponse> getQuotesByCustomer(UUID customerId, Pageable pageable) {
        return quoteRepository.findByCustomerId(customerId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public QuoteResponse getQuoteById(UUID id) {
        Quote quote = quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));
        return mapper.toResponse(quote);
    }

    public QuoteResponse updateQuote(UUID id, QuoteUpdateRequest request) {
        Quote quote = quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));

        if (quote.getStatus() != Quote.Status.PENDING) {
            throw new BadRequestException("Can only update pending quotes");
        }

        mapper.updateEntity(quote, request);
        return mapper.toResponse(quoteRepository.save(quote));
    }

    public QuoteResponse respondToQuote(UUID id, String action) {
        Quote quote = quoteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quote", "id", id));

        if (quote.getStatus() != Quote.Status.PENDING) {
            throw new BadRequestException("Can only respond to pending quotes");
        }

        if ("ACCEPT".equalsIgnoreCase(action)) {
            quote.setStatus(Quote.Status.ACCEPTED);
        } else if ("REJECT".equalsIgnoreCase(action)) {
            quote.setStatus(Quote.Status.REJECTED);
        } else {
            throw new BadRequestException("Invalid action. Use ACCEPT or REJECT");
        }

        quote.setRespondedAt(LocalDateTime.now());
        return mapper.toResponse(quoteRepository.save(quote));
    }

    public void deleteQuote(UUID id) {
        if (!quoteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quote", "id", id);
        }
        quoteRepository.deleteById(id);
    }
}
