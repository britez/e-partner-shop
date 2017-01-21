package com.epartner.mercadopago.push.strategies;

import com.epartner.mercadopago.ipn.MercadoPagoIPNPush;

/**
 * Created by maty on 21/1/17.
 */
public interface MercadoPagoIPNEvent {

  public void execute(MercadoPagoIPNPush data);
}
