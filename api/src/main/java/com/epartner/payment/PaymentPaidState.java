package com.epartner.payment;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import org.springframework.stereotype.Component;

/**
 * Created by maty on 3/12/16.
 */
@Component
public class PaymentPaidState implements PaymentChangedState {


    @Override
    public Payment execute(Payment payment) {

        payment.setState(PaymentState.PAID);
        return payment;
    }

    @Override
    public boolean apply(PaymentState type) {
        return type.equals(PaymentState.PAID);
    }
}
