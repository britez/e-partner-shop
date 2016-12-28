package com.epartner.resources;

import com.epartner.representations.MercadoPagoPaymentRepresentation;
import com.epartner.representations.PaymentRepresentation;
import com.epartner.services.MercadoPagoService;
import com.epartner.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maty on 8/12/16.
 */
@RequestMapping(value = MercadoPagoResource.MERCADO_PAGO)
@RestController
public class MercadoPagoResource {

    static final String MERCADO_PAGO = "api/me/mercado-pago";

    private MercadoPagoService mercadoPagoService;
    private PaymentService paymentService;

    @Autowired
    public MercadoPagoResource(MercadoPagoService mercadoPagoService, PaymentService paymentService){

        this.mercadoPagoService = mercadoPagoService;
        this.paymentService = paymentService;
    }

    private String getPrincipal() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(method = RequestMethod.POST)
    public MercadoPagoPaymentRepresentation create(@RequestBody PaymentRepresentation paymentRepresentation){

        paymentRepresentation.setUser(getPrincipal());

        //crear el payment y que solo sea de mercado pago
            return mercadoPagoService
                .createMercadoPagoPayment(
                        paymentService.createAllowed(paymentRepresentation)
                );
    }

}
