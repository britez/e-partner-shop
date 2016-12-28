package com.epartner.resources;

import com.epartner.representations.PaymentRepresentation;
import com.epartner.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
@RequestMapping(value = PublicPaymentResource.PAYMENT)
@RestController
public class PublicPaymentResource {

    public static final String PAYMENT = "api/payments";

    private PaymentService paymentService;

    @Autowired()
    public PublicPaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public PaymentRepresentation create(@RequestBody PaymentRepresentation paymentRepresentation){
        return paymentService.create(paymentRepresentation);
    }

}
