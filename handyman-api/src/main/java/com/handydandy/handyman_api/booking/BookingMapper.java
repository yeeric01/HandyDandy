package com.handydandy.handyman_api.booking;

import com.handydandy.handyman_api.booking.dto.BookingCreateRequest;
import com.handydandy.handyman_api.booking.dto.BookingResponse;
import com.handydandy.handyman_api.booking.dto.BookingUpdateRequest;
import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.quote.Quote;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapper {

    public Booking toEntity(BookingCreateRequest request, Profile customer, Profile handyman,
                            ServiceCategory category, Location location, Quote quote) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setHandyman(handyman);
        booking.setCategory(category);
        booking.setLocation(location);
        booking.setQuote(quote);
        booking.setScheduledStart(request.scheduledStart());
        booking.setScheduledEnd(request.scheduledEnd());
        booking.setDescription(request.description());
        booking.setEstimatedCost(request.estimatedCost());
        return booking;
    }

    public BookingResponse toResponse(Booking booking) {
        BookingResponse.ProfileSummary customerSummary = null;
        if (booking.getCustomer() != null) {
            Profile c = booking.getCustomer();
            customerSummary = new BookingResponse.ProfileSummary(c.getId(), c.getName(), c.getEmail());
        }

        BookingResponse.ProfileSummary handymanSummary = null;
        if (booking.getHandyman() != null) {
            Profile h = booking.getHandyman();
            handymanSummary = new BookingResponse.ProfileSummary(h.getId(), h.getName(), h.getEmail());
        }

        BookingResponse.CategorySummary categorySummary = null;
        if (booking.getCategory() != null) {
            ServiceCategory cat = booking.getCategory();
            categorySummary = new BookingResponse.CategorySummary(cat.getId(), cat.getName());
        }

        BookingResponse.LocationSummary locationSummary = null;
        if (booking.getLocation() != null) {
            Location loc = booking.getLocation();
            locationSummary = new BookingResponse.LocationSummary(loc.getId(), loc.getCity(), loc.getState(), loc.getZip());
        }

        return new BookingResponse(
            booking.getId(),
            customerSummary,
            handymanSummary,
            categorySummary,
            locationSummary,
            booking.getQuote() != null ? booking.getQuote().getId() : null,
            booking.getScheduledStart(),
            booking.getScheduledEnd(),
            booking.getDescription(),
            booking.getStatus() != null ? booking.getStatus().name() : null,
            booking.getEstimatedCost(),
            booking.getFinalCost(),
            booking.getCreatedAt(),
            booking.getUpdatedAt(),
            booking.getCompletedAt()
        );
    }

    public void updateEntity(Booking booking, BookingUpdateRequest request, Location location) {
        if (request.scheduledStart() != null) {
            booking.setScheduledStart(request.scheduledStart());
        }
        if (request.scheduledEnd() != null) {
            booking.setScheduledEnd(request.scheduledEnd());
        }
        if (request.description() != null) {
            booking.setDescription(request.description());
        }
        if (request.status() != null) {
            Booking.Status newStatus = Booking.Status.valueOf(request.status().toUpperCase());
            booking.setStatus(newStatus);
            if (newStatus == Booking.Status.COMPLETED) {
                booking.setCompletedAt(LocalDateTime.now());
            }
        }
        if (request.estimatedCost() != null) {
            booking.setEstimatedCost(request.estimatedCost());
        }
        if (request.finalCost() != null) {
            booking.setFinalCost(request.finalCost());
        }
        if (location != null) {
            booking.setLocation(location);
        }
        booking.setUpdatedAt(LocalDateTime.now());
    }
}
