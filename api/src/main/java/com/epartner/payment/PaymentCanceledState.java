package com.epartner.payment;

import com.epartner.converters.ProductConverter;
import com.epartner.domain.Payment;
import com.epartner.domain.PaymentState;
import com.epartner.domain.PaymentType;
import com.epartner.domain.Product;
import com.epartner.representations.ProductRepresentation;
import com.epartner.services.ProductImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by maty on 3/12/16.
 */
@Component
public class PaymentCanceledState implements PaymentChangedState {

    private final ProductImportService productImportService;
    private final ProductConverter productConverter;

    @Autowired
    PaymentCanceledState(ProductImportService productImportService,
                         ProductConverter productConverter) {
        this.productImportService = productImportService;
        this.productConverter = productConverter;
    }

    @Override
    public Payment execute(Payment payment) {

        Product product = payment.getProduct();
        if(product.getImported()){
            //agregar el stock a meli
            ProductRepresentation productRepresentation = this.productConverter.convert(payment.getProduct());
            productRepresentation.setStock(productRepresentation.getStock() + payment.getQuantity());
            this.productImportService.updateStock(productRepresentation);
        }
        product.addStock(payment.getQuantity());
        payment.setState(PaymentState.CANCELED);

        return payment;
    }

    @Override
    public boolean apply(PaymentState type) {

        return type.equals(PaymentState.CANCELED);
    }


}
