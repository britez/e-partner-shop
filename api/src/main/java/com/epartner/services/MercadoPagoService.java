package com.epartner.services;

import com.epartner.exceptions.PaymentException;
import com.epartner.mercadopago.MercadoPagoBackUrls;
import com.epartner.mercadopago.MercadoPagoButtonData;
import com.epartner.mercadopago.MercadoPagoItem;
import com.epartner.representations.MercadoPagoPaymentRepresentation;
import com.epartner.representations.PaymentRepresentation;
import com.mercadopago.MP;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maty on 8/12/16.
 */
@Component
public class MercadoPagoService {

    private static final String SUCCES_URL = "http://localhost:18111/#/buy-success";
    private static final String NOTIFICATION_URL = "http://ec2-35-163-50-117.us-west-2.compute.amazonaws.com:18100/api/meli/notification";
    //private static final String failureUrl = "http://localhosts:8080/lala";
    //private static final String pendingUrl = "http://localhosts:8080/lala";

    @Value("${epartner.mercadopago.clientId}")
    private String CLIENT_ID;

    @Value("${epartner.mercadopago.secretId}")
    private String SECRET_ID;

    private ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);

    public MercadoPagoService() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public MercadoPagoPaymentRepresentation createMercadoPagoPayment(PaymentRepresentation payment){

        String paymentUrl;
        try {
            MP mp = new MP(CLIENT_ID, SECRET_ID);


            String jsonData = createJSONData(payment);


            logger.info("Creando preferencias con el json: " + jsonData);
            JSONObject preference = mp.createPreference(jsonData);

            String mercadoPagoUrlExtractor = "sandbox_init_point";

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
        buttonData.setNotificationUrl(NOTIFICATION_URL);
        buttonData.setExternalReference(payment.getId().toString());


        return mapper.writeValueAsString(buttonData);
    }

    private List<MercadoPagoItem>  createItems(PaymentRepresentation payment) {

        MercadoPagoItem item = new MercadoPagoItem();
        item.setDescription(payment.getProduct().getDescription());
        item.setTitle(payment.getProduct().getName());
        item.setId(payment.getProduct().getId().toString());
        item.setPictureUrl(payment.getProduct().getUrl());
        item.setQuantity(payment.getQuantity());
        item.setUnitPrice(payment.getPrice());

        List<MercadoPagoItem> items = new ArrayList<>();
        items.add(item);

        return items;
    }

    private MercadoPagoBackUrls createBackUrls(){

        MercadoPagoBackUrls backUrls = new MercadoPagoBackUrls();
        //backUrls.setFailure(failureUrl);
        //backUrls.setPending(pendingUrl);
        backUrls.setSuccess(SUCCES_URL);

        return backUrls;
    }


}
