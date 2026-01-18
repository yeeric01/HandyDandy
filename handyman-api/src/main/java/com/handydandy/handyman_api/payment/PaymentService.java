package com.handydandy.handyman_api.payment;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.booking.BookingRepository;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.payment.dto.PaymentCreateRequest;
import com.handydandy.handyman_api.payment.dto.PaymentResponse;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final ProfileRepository profileRepository;
    private final PaymentMapper mapper;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository,
                          ProfileRepository profileRepository,
                          PaymentMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    public PaymentResponse createPayment(PaymentCreateRequest request) {
        Booking booking = bookingRepository.findById(request.bookingId())
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", request.bookingId()));

        Profile payer = profileRepository.findById(request.payerId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.payerId()));

        Profile payee = profileRepository.findById(request.payeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.payeeId()));

        Payment payment = mapper.toEntity(request, booking, payer, payee);
        return mapper.toResponse(paymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByBooking(UUID bookingId) {
        return paymentRepository.findByBookingId(bookingId).stream()
            .map(mapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByPayer(UUID payerId, Pageable pageable) {
        return paymentRepository.findByPayerId(payerId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByPayee(UUID payeeId, Pageable pageable) {
        return paymentRepository.findByPayeeId(payeeId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return mapper.toResponse(payment);
    }

    public PaymentResponse processPayment(UUID id, String transactionId) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        payment.setTransactionId(transactionId);
        payment.setStatus(Payment.Status.COMPLETED);
        payment.setProcessedAt(LocalDateTime.now());

        return mapper.toResponse(paymentRepository.save(payment));
    }

    public PaymentResponse refundPayment(UUID id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        payment.setStatus(Payment.Status.REFUNDED);
        payment.setProcessedAt(LocalDateTime.now());

        return mapper.toResponse(paymentRepository.save(payment));
    }
}
