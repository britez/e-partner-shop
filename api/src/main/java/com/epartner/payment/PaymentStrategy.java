package com.epartner.payment;

import com.epartner.domain.Payment;
import com.epartner.representations.PaymentRepresentation;

/**
 * Created by mapsi on 12/1/16.
 */
public interface PaymentStrategy {


    boolean apply(String type);
    Payment execute(PaymentRepresentation paymentRepresentation);
}
