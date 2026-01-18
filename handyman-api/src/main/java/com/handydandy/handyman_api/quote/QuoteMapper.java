package com.handydandy.handyman_api.quote;

import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.quote.dto.QuoteCreateRequest;
import com.handydandy.handyman_api.quote.dto.QuoteResponse;
import com.handydandy.handyman_api.quote.dto.QuoteUpdateRequest;
import com.handydandy.handyman_api.servicerequest.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {

    public Quote toEntity(QuoteCreateRequest request, Profile handyman, Profile customer,
                          ServiceRequest serviceRequest, ServiceCategory category) {
        Quote quote = new Quote();
        quote.setHandyman(handyman);
        quote.setCustomer(customer);
        quote.setServiceRequest(serviceRequest);
        quote.setCategory(category);
        quote.setLaborCost(request.laborCost());
        quote.setMaterialsCost(request.materialsCost());
        quote.setTotalCost(request.totalCost());
        quote.setEstimatedHours(request.estimatedHours());
        quote.setDescription(request.description());
        quote.setTermsAndConditions(request.termsAndConditions());
        quote.setValidUntil(request.validUntil());
        return quote;
    }

    public QuoteResponse toResponse(Quote quote) {
        QuoteResponse.ProfileSummary handymanSummary = null;
        if (quote.getHandyman() != null) {
            Profile h = quote.getHandyman();
            handymanSummary = new QuoteResponse.ProfileSummary(h.getId(), h.getName(), h.getEmail());
        }

        QuoteResponse.ProfileSummary customerSummary = null;
        if (quote.getCustomer() != null) {
            Profile c = quote.getCustomer();
            customerSummary = new QuoteResponse.ProfileSummary(c.getId(), c.getName(), c.getEmail());
        }

        QuoteResponse.CategorySummary categorySummary = null;
        if (quote.getCategory() != null) {
            ServiceCategory cat = quote.getCategory();
            categorySummary = new QuoteResponse.CategorySummary(cat.getId(), cat.getName());
        }

        return new QuoteResponse(
            quote.getId(),
            handymanSummary,
            customerSummary,
            quote.getServiceRequest() != null ? quote.getServiceRequest().getId() : null,
            categorySummary,
            quote.getLaborCost(),
            quote.getMaterialsCost(),
            quote.getTotalCost(),
            quote.getEstimatedHours(),
            quote.getDescription(),
            quote.getTermsAndConditions(),
            quote.getValidUntil(),
            quote.getStatus() != null ? quote.getStatus().name() : null,
            quote.getCreatedAt(),
            quote.getRespondedAt()
        );
    }

    public void updateEntity(Quote quote, QuoteUpdateRequest request) {
        if (request.laborCost() != null) {
            quote.setLaborCost(request.laborCost());
        }
        if (request.materialsCost() != null) {
            quote.setMaterialsCost(request.materialsCost());
        }
        if (request.totalCost() != null) {
            quote.setTotalCost(request.totalCost());
        }
        if (request.estimatedHours() != null) {
            quote.setEstimatedHours(request.estimatedHours());
        }
        if (request.description() != null) {
            quote.setDescription(request.description());
        }
        if (request.termsAndConditions() != null) {
            quote.setTermsAndConditions(request.termsAndConditions());
        }
        if (request.validUntil() != null) {
            quote.setValidUntil(request.validUntil());
        }
    }
}
