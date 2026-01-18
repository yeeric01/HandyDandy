package com.handydandy.handyman_api.payment;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.payment.dto.PaymentCreateRequest;
import com.handydandy.handyman_api.payment.dto.PaymentResponse;
import com.handydandy.handyman_api.profile.Profile;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentCreateRequest request, Booking booking, Profile payer, Profile payee) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPayer(payer);
        payment.setPayee(payee);
        payment.setAmount(request.amount());
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(request.paymentMethod().toUpperCase()));
        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {
        PaymentResponse.ProfileSummary payerSummary = null;
        if (payment.getPayer() != null) {
            Profile p = payment.getPayer();
            payerSummary = new PaymentResponse.ProfileSummary(p.getId(), p.getName(), p.getEmail());
        }

        PaymentResponse.ProfileSummary payeeSummary = null;
        if (payment.getPayee() != null) {
            Profile p = payment.getPayee();
            payeeSummary = new PaymentResponse.ProfileSummary(p.getId(), p.getName(), p.getEmail());
        }

        return new PaymentResponse(
            payment.getId(),
            payment.getBooking() != null ? payment.getBooking().getId() : null,
            payerSummary,
            payeeSummary,
            payment.getAmount(),
            payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null,
            payment.getStatus() != null ? payment.getStatus().name() : null,
            payment.getTransactionId(),
            payment.getCreatedAt(),
            payment.getProcessedAt()
        );
    }
}
