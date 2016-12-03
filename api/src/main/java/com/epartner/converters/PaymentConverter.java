package com.epartner.converters;

import com.epartner.domain.Payment;
import com.epartner.domain.Product;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.PaymentRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mapsi on 11/26/16.
 */
@Component
public class PaymentConverter {


    public PaymentRepresentation convert(Payment payment) {
        PaymentRepresentation representation = new PaymentRepresentation();
        representation.setId(payment.getId());
        representation.setPaymentType(payment.getPaymenType());
        representation.setProductId(payment.getProduct().getId());
        representation.setQuantity(payment.getQuantity());
        representation.setState(payment.getState());
        return representation;
    }

    public Payment convert(PaymentRepresentation paymentRepresentation) {
        Payment payment = new Payment();
        payment.setId(paymentRepresentation.getId());
        payment.setPaymenType(paymentRepresentation.getPaymentType());
        payment.setQuantity(paymentRepresentation.getQuantity());
        payment.setState(paymentRepresentation.getState());
        return payment;
    }

    public List<PaymentRepresentation> convert(Page<Payment> stored) {
        return stored
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
