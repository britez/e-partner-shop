package com.epartner.resources;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epartner.representations.MercadoPagoPaymentRepresentation;
import com.epartner.representations.PaymentRepresentation;
import com.mercadopago.MP;

/**
 * Created by maty on 21/12/16.
 */
@RequestMapping(value = MercadoPagoNotificationResource.MERCADO_PAGO)
@RestController
public class MercadoPagoNotificationResource {

  public static final String MERCADO_PAGO = "api/meli/notification";

  Logger logger = LoggerFactory.getLogger(MercadoPagoNotificationResource.class);

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void create(@RequestParam(value = "topic", required = false) String topic,
                     @RequestParam(value = "id", required = false) String id){


      final String CLIENT_ID = "6004361641371424";
      final String SECRET_ID = "dRjMmDt6nN8va0j9Qqcyp3w7rAKiGHV7";

    MP mp = new MP(CLIENT_ID, SECRET_ID);

    try {

      JSONObject payment = mp.get("/payments/"+id);
      logger.info(payment.toString());

    } catch (Exception e) {
      logger.error("Rompio al llamar al servicio",e);
    }

  }
}
