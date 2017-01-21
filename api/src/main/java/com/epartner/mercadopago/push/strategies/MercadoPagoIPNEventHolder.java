package com.epartner.mercadopago.push.strategies;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epartner.mercadopago.MercadoIPNStatus;

/**
 * Created by maty on 21/1/17.
 */
@Component
public class MercadoPagoIPNEventHolder {

  private Map<String, Optional<MercadoPagoIPNEvent>> strategies;

  private static final Logger logger = LoggerFactory.getLogger(MercadoPagoIPNEventHolder.class);

  @Autowired
  public MercadoPagoIPNEventHolder(
    AcceptedPaymentMercadoIPNEvent acceptedPaymentMercadoIPNEvent) {

    this.strategies = new HashMap<>();
    this.strategies.put(MercadoIPNStatus.APPROVED, Optional.of(acceptedPaymentMercadoIPNEvent));
  }


  public MercadoPagoIPNEvent getEventStrategy(String eventStatus){

    logger.info("Buscado estrategia de resolucion de IPN para el estado [" + eventStatus + "]");
    return this.strategies
      .get(eventStatus)
      .orElseThrow(InvalidIPNPushStatus::new);
  }


}
