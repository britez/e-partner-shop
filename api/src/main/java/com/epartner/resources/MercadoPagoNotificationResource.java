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
  private static final String CLIENT_ID = "5388494152368678";
  private static final String SECRET_ID = "6LCadMJuqFsIdVR61jj7j3trfZHc9ucO";

  Logger logger = LoggerFactory.getLogger(MercadoPagoNotificationResource.class);

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void create(@RequestParam(value = "topic", required = false) String topic,
                     @RequestParam(value = "id", required = false) String id){

    MP mp = new MP(CLIENT_ID, SECRET_ID);

    try {
      logger.info("El topic: " + topic);
      logger.info("El id del payment: " + id);

      mp.sandboxMode(true);

      JSONObject info = mp.getPaymentInfo(id);
      logger.info("Payment info: " + info);

      JSONObject info2 = mp.getPayment(id);
      logger.info("Payment: " + info2);

      JSONObject info3 = mp.getPreapprovalPayment(id);
      logger.info("Pre Appr Payment: " + info3);

      JSONObject info4 = mp.getAuthorizedPayment(id);
      logger.info("Auth Payment: " + info4);

      //JSONObject payment = mp.get("/payments/"+id);
      //logger.info(payment.toString());

    } catch (Exception e) {
      logger.error("Rompio al llamar al servicio",e);
    }

  }
}
