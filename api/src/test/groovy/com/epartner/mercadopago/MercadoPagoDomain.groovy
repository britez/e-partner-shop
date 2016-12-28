package com.epartner.mercadopago

import org.codehaus.jackson.map.ObjectMapper
import spock.lang.Specification

/**
 * Created by maty on 9/12/16.
 */
class MercadoPagoDomain extends Specification{



    void "que se puede hacer el marshal del objeto MercadoPagoButtonData"(){


       def data =  new MercadoPagoButtonData()

        def items = []
        def unItem = new MercadoPagoItem()
        unItem.setId("1")
        unItem.setDescription("lalala")
        unItem.setPictureUrl("http://lalala.com")
        unItem.setQuantity("1")
        unItem.setTitle("dasdasda")
        unItem.setUnitPrice("12000")

        items.add(unItem)
        data.setItems(items)
        data.setExternalReference("12")

        def backUrls = new MercadoPagoBackUrls()
        backUrls.setFailure("failure")
        backUrls.setSuccess("Success")
        backUrls.setPending("pending")

        data.setBackUrls(backUrls)
        data.setNotificationUrl("http://notification")

        ObjectMapper objectMapper = new ObjectMapper()


        println(objectMapper.writeValueAsString(data))

        expect: true

    }
}
