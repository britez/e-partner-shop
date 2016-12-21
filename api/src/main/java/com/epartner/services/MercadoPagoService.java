package com.epartner.services;

import com.epartner.exceptions.PaymentException;
import com.epartner.mercadopago.MercadoPagoBackUrls;
import com.epartner.mercadopago.MercadoPagoButtonData;
import com.epartner.mercadopago.MercadoPagoItem;
import com.epartner.representations.MercadoPagoPaymentRepresentation;
import com.epartner.representations.PaymentRepresentation;
import com.mercadopago.MP;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maty on 8/12/16.
 */
@Component
public class MercadoPagoService {

    private static final String CURRENCY = "\"ARS\"";
    private static final String notificationUrl = "\"http://localhosts:8080/lala\"";

    private static final String succesUrl = "\"http://localhosts:8080/lala\"";
    private static final String failureUrl = "\"http://localhosts:8080/lala\"";
    private static final String pendingUrl = "\"http://localhosts:8080/lala\"";
    private static final String CLIENT_ID = "6004361641371424";
    private static final String SECRET_ID = "dRjMmDt6nN8va0j9Qqcyp3w7rAKiGHV7";

    private Boolean sandboxMode = true;
    private String mercadoPagoUrlExtractor="sandbox_init_point";

    @Autowired
    ObjectMapper mapper;

    Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);

        public MercadoPagoPaymentRepresentation createMercadoPagoPayment(PaymentRepresentation payment){

            String paymentUrl;
            try {
                MP mp = new MP(CLIENT_ID, SECRET_ID);

                mp.sandboxMode(sandboxMode);

                String jsonData = createJSONData(payment);

                logger.info("Creando preferencias con el json: " + jsonData);
                JSONObject preference = mp.createPreference(jsonData);

                paymentUrl = preference.getJSONObject("response").getString(mercadoPagoUrlExtractor);
                logger.info("se creo al url de pago ["+ paymentUrl+"]");
            }catch (JSONException json){

                logger.error("Errro al parsear el JSON de pago");
                throw new PaymentException();
            }catch (Exception e){

                logger.error("Error al instanciar el cliente de mercado pago ");
                throw new PaymentException();
            }


            return new MercadoPagoPaymentRepresentation(paymentUrl);

        }

        private String createJSONData(PaymentRepresentation payment) throws IOException {

            MercadoPagoButtonData buttonData = new MercadoPagoButtonData();
            buttonData.setItems(createItems(payment));
            buttonData.setBackUrls(createBackUrls());
            buttonData.setNotificationUrl(notificationUrl);
            buttonData.setExternalReference(payment.getId().toString());


            return mapper.writeValueAsString(buttonData);
        }

    private List<MercadoPagoItem>  createItems(PaymentRepresentation payment) {

        MercadoPagoItem item = new MercadoPagoItem();
        item.setDescription(payment.getProduct().getDescription());
        item.setTitle(payment.getProduct().getName());
        item.setId(payment.getProduct().getId().toString());
        item.setPictureUrl(payment.getProduct().getUrl());
        item.setQuantity(payment.getQuantity().toString());
        item.setUnitPrice(payment.getPrice().toString());

        List<MercadoPagoItem> items = new ArrayList<>();
        items.add(item);

        return items;
    }

    private MercadoPagoBackUrls createBackUrls(){

        MercadoPagoBackUrls backUrls = new MercadoPagoBackUrls();
        backUrls.setFailure(failureUrl);
        backUrls.setPending(pendingUrl);
        backUrls.setSuccess(succesUrl);

        return backUrls;
    }


}
