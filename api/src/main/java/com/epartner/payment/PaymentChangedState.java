package com.epartner.payment;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import com.epartner.repositories.ProductRepository;

/**
 * Created by maty on 3/12/16.
 */
public interface PaymentChangedState {

    Payment execute(Payment payment);
    boolean apply(PaymentState type);
}
