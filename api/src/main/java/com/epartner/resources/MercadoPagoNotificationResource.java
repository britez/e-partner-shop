package com.epartner.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epartner.representations.MercadoPagoPaymentRepresentation;
import com.epartner.representations.PaymentRepresentation;

/**
 * Created by maty on 21/12/16.
 */
@RequestMapping(value = MercadoPagoResource.MERCADO_PAGO)
@RestController
public class MercadoPagoNotificationResource {

  public static final String MERCADO_PAGO = "api/meli/notification";

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void create(@RequestParam(value = "topic", required = false) String topic,
                     @RequestParam(value = "id", required = false) String id){




  }
}
