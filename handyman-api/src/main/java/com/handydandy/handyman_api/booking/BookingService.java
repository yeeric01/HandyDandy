package com.handydandy.handyman_api.booking;

import com.handydandy.handyman_api.booking.dto.BookingCreateRequest;
import com.handydandy.handyman_api.booking.dto.BookingResponse;
import com.handydandy.handyman_api.booking.dto.BookingUpdateRequest;
import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.category.ServiceCategoryRepository;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.location.LocationRepository;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import com.handydandy.handyman_api.quote.Quote;
import com.handydandy.handyman_api.quote.QuoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ProfileRepository profileRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final QuoteRepository quoteRepository;
    private final BookingMapper mapper;

    public BookingService(BookingRepository bookingRepository,
                          ProfileRepository profileRepository,
                          ServiceCategoryRepository categoryRepository,
                          LocationRepository locationRepository,
                          QuoteRepository quoteRepository,
                          BookingMapper mapper) {
        this.bookingRepository = bookingRepository;
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.quoteRepository = quoteRepository;
        this.mapper = mapper;
    }

    public BookingResponse createBooking(BookingCreateRequest request) {
        Profile customer = profileRepository.findById(request.customerId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.customerId()));

        Profile handyman = profileRepository.findById(request.handymanId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));

        ServiceCategory category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", request.categoryId()));

        Location location = locationRepository.findById(request.locationId())
            .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));

        Quote quote = null;
        if (request.quoteId() != null) {
            quote = quoteRepository.findById(request.quoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Quote", "id", request.quoteId()));
        }

        Booking booking = mapper.toEntity(request, customer, handyman, category, location, quote);
        return mapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> getBookingsByCustomer(UUID customerId, Pageable pageable) {
        return bookingRepository.findByCustomerId(customerId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> getBookingsByHandyman(UUID handymanId, Pageable pageable) {
        return bookingRepository.findByHandymanId(handymanId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> getBookingsByStatus(String status, Pageable pageable) {
        Booking.Status statusEnum = Booking.Status.valueOf(status.toUpperCase());
        return bookingRepository.findByStatus(statusEnum, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(UUID id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return mapper.toResponse(booking);
    }

    public BookingResponse updateBooking(UUID id, BookingUpdateRequest request) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));
        }

        mapper.updateEntity(booking, request, location);
        return mapper.toResponse(bookingRepository.save(booking));
    }

    public void deleteBooking(UUID id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking", "id", id);
        }
        bookingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Booking getEntityById(UUID id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
    }
}
