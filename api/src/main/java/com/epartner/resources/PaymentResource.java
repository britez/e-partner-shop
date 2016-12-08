package com.epartner.resources;

import com.epartner.representations.CategoryRepresentation;
import com.epartner.representations.PaymentRepresentation;
import com.epartner.representations.ProductRepresentation;
import com.epartner.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
@RequestMapping(value = PaymentResource.PAYMENT)
@RestController
public class PaymentResource {

    public static final String PAYMENT = "api/admin/me/payments";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_MAX = "10";
    public static final String ID = "/{id}";

    private PaymentService paymentService;

    @Autowired()
    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public PaymentRepresentation create(@RequestBody PaymentRepresentation paymentRepresentation){
        return paymentService.create(paymentRepresentation);
    }

    @RequestMapping(method = RequestMethod.GET, value = ID)
    public PaymentRepresentation get(@PathVariable Long id) {
        return paymentService.show(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<PaymentRepresentation> list(
            @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(required = false) String query){
        return this.paymentService.getAllPayments(
                Optional.ofNullable(max),
                Optional.ofNullable(page),
                Optional.ofNullable(query));
    }


    @RequestMapping(value = ID, method = RequestMethod.PUT)
    public PaymentRepresentation update(@PathVariable Long id, @RequestBody PaymentRepresentation paymentRepresentation){
        return paymentService.update(id, paymentRepresentation);
    }


}
