package com.epartner.resources;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epartner.domain.Payment;
import com.epartner.mercadopago.ipn.MercadoPagoIPNPush;

import org.codehaus.jackson.map.ObjectMapper;

import com.epartner.mercadopago.push.strategies.MercadoPagoIPNEvent;
import com.epartner.mercadopago.push.strategies.MercadoPagoIPNEventHolder;
import com.mercadopago.MP;

/**
 * Created by maty on 21/12/16.
 */
@RequestMapping(value = MercadoPagoNotificationResource.MERCADO_PAGO)
@RestController
public class MercadoPagoNotificationResource {

  public static final String MERCADO_PAGO = "api/meli/notification";

  @Value("${epartner.mercadopago.clientId}")
  private String CLIENT_ID;

  @Value("${epartner.mercadopago.secretId}")
  private String SECRET_ID;

  Logger logger = LoggerFactory.getLogger(MercadoPagoNotificationResource.class);

  private static final String PAYMENT_TOPIC = "payment";
  private static final String MERCHANT_ORDER_TOPIC = "merchant_order";

  private final MercadoPagoIPNEventHolder mercadoPagoIPNEventHolder;

  @Autowired
  public MercadoPagoNotificationResource(MercadoPagoIPNEventHolder mercadoPagoIPNEventHolder) {
    this.mercadoPagoIPNEventHolder = mercadoPagoIPNEventHolder;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void create(@RequestParam(value = "topic", required = false) String topic,
                     @RequestParam(value = "id", required = false) String id){

    MP mp = new MP(CLIENT_ID, SECRET_ID);
    ObjectMapper mapper = new ObjectMapper();

    try {
      logger.info("El topic: " + topic);
      logger.info("El id del payment: " + id);

      JSONObject merchant = mp.get("/merchant_orders/"+id);
      logger.info("payments:" + merchant.getJSONObject("response").getJSONObject("payments"));

      if(PAYMENT_TOPIC.equals(topic)){

        MercadoPagoIPNPush pushData = mapper.readValue(merchant.toString(), MercadoPagoIPNPush.class);

        MercadoPagoIPNEvent eventStrategy = mercadoPagoIPNEventHolder.getEventStrategy(pushData
          .getResponse()
          .getCollection()
          .getStatus());
        eventStrategy.execute(pushData);

      }else {

        logger.info("Merchant order: ", merchant.toString());
      }

    } catch (Exception e) {
      logger.error("Rompio al llamar al servicio",e);
    }

  }
}
