package com.epartner.mercadopago.push.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epartner.domain.PaymentState;
import com.epartner.mercadopago.ipn.MercadoPagoIPNPush;
import com.epartner.representations.PaymentRepresentation;
import com.epartner.services.MercadoPagoService;
import com.epartner.services.PaymentService;

/**
 * Created by maty on 21/1/17.
 */
@Component
public class AcceptedPaymentMercadoIPNEvent implements MercadoPagoIPNEvent {


  private PaymentService paymentService;
  private static final Logger logger = LoggerFactory.getLogger(AcceptedPaymentMercadoIPNEvent.class);

  @Autowired
  public AcceptedPaymentMercadoIPNEvent(PaymentService paymentService) {

    this.paymentService = paymentService;
  }

  @Override
  public void execute(MercadoPagoIPNPush data) {

    //extraer a algun lugar se va usar por varios
    // lados falta manejor de errores

    Long paymentId = Long.valueOf(data.getResponse().getCollection().getExternalReference());
    logger.info("Marcando el pago [" + paymentId + "] como pagado");

    PaymentRepresentation payment = paymentService.show(Long.valueOf(paymentId));
    payment.setState(PaymentState.PAID);
    //hay que remover el stock??
    
    paymentService.update(paymentId, payment);

  }
}
