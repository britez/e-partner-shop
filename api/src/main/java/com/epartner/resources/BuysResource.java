package com.epartner.resources;

import com.epartner.representations.PaymentRepresentation;
import com.epartner.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by mapsi on 11/26/16.
 */
@RequestMapping(value = BuysResource.URL)
@RestController
public class BuysResource {

    public static final String URL = "api/me/buys";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_MAX = "10";
    public static final String ID = "/{id}";

    private PaymentService paymentService;

    @Autowired()
    public BuysResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = RequestMethod.GET, value = ID)
    public PaymentRepresentation get(@PathVariable Long id) {
        return paymentService.showByUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<PaymentRepresentation> list(
            @RequestParam(required = false, defaultValue = DEFAULT_MAX) Integer max,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(required = false) String query){
        return this.paymentService.getAllPaymentsByUser(
                Optional.ofNullable(max),
                Optional.ofNullable(page),
                Optional.ofNullable(query));
    }

}
