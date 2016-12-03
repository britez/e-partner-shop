package com.epartner.payment;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentType;
import com.epartner.representations.PaymentRepresentation;
import org.springframework.stereotype.Component;

/**
 * Created by mapsi on 12/1/16.
 */
@Component
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean apply(String type) {

        return PaymentType.CASH.equals(type);
    }

    @Override
    public Payment execute(PaymentRepresentation paymentRepresentation) {
        return null;
    }
}
