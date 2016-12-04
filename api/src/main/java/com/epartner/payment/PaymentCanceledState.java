package com.epartner.payment;

import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import com.epartner.domain.PaymentType;
import com.epartner.domain.Product;
import org.springframework.stereotype.Component;

/**
 * Created by maty on 3/12/16.
 */
@Component
public class PaymentCanceledState implements PaymentChangedState {




    @Override
    public Payment execute(Payment payment) {

        if(payment.getPaymenType().equals(PaymentType.MERCADO_PAGO)){
          //agregar el stock a meli
        }
        Product product = payment.getProduct();

        product.addStock(payment.getQuantity());

        payment.setState(PaymentState.CANCELED);

        return payment;
    }

    @Override
    public boolean apply(PaymentState type) {

        return type.equals(PaymentState.CANCELED);
    }


}
