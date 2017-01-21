package com.epartner.resources

import com.epartner.mercadopago.ipn.MercadoPagoIPNPush
import com.epartner.representations.ProductRepresentation
import com.epartner.services.ProductService
import com.fasterxml.jackson.databind.DeserializationFeature
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

/**
 * Created by mbritez on 3/09/16.
 */
class ProductResourceTests extends Specification {

    private ProductResource resource
    private ProductService mockedService

    def setup(){
        mockedService = Mock(ProductService)
        resource = new ProductResource(mockedService)
    }

    def "test create"(){
        setup:
        def mockedRepresentation = Mock(ProductRepresentation)
        when:
        def result = resource.create(mockedRepresentation)
        then:
        1 * mockedService.create(mockedRepresentation) >> mockedRepresentation
        result
        result == mockedRepresentation
    }

    def "test update"() {
        setup:
        def mockedRepresentation = Mock(ProductRepresentation)
        when:
        def result = resource.update(mockedRepresentation, 1L)
        then:
        1 * mockedService.update(mockedRepresentation, 1L) >> mockedRepresentation
        result
        result == mockedRepresentation
    }

    def "test delete"() {
        when:
        resource.delete(1L)
        then:
        1 * mockedService.delete(1L)
    }

    def "test get"(){
        setup:
        def mockedRepresentation = Mock(ProductRepresentation)
        when:
        def result = resource.get(1L)
        then:
        1 * mockedService.show(1L) >> mockedRepresentation
        result
    }

    def "test list"() {
        setup:
        def mockedProduct = Mock(ProductRepresentation)
        def mockedResult = new PageImpl([mockedProduct])
        def query = ""

        when:
        def result = resource.list(10, 0, query)
        then:
        1 * mockedService.list(Optional.of(""), Optional.of(10), Optional.of(0)) >> mockedResult
        result
        def product = result.getContent().get(0)
        product
        product == mockedProduct
    }

    def "test" () {
        setup:
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        when:
        String paymentObject = '{"status":200,"response":{"collection":{"id":2549281953,"site_id":"MLA","date_created":"2017-01-21T18:30:25.000-04:00","date_approved":null,"money_release_date":null,"last_modified":"2017-01-21T18:33:14.406-04:00","payer":{"id":193053497,"first_name":"","last_name":"mati","phone":{"area_code":null,"number":null,"extension":""},"identification":{"type":"DNI","number":"31234595"},"email":"mati@redb.ee","nickname":"@193053497"},"order_id":"4","external_reference":"4","merchant_order_id":449305115,"reason":"Item De Testeo-no Ofertar-reloj Casio Mtp-v006d-1b Hombre","currency_id":"ARS","transaction_amount":4000,"net_received_amount":3760.4,"total_paid_amount":4000,"shipping_cost":0,"coupon_amount":0,"coupon_fee":0,"finance_fee":0,"discount_fee":0,"coupon_id":null,"status":"cancelled","status_detail":"by_payer","installments":1,"issuer_id":310,"installment_amount":4000,"deferred_period":null,"payment_type":"credit_card","marketplace":"NONE","operation_type":"regular_payment","transaction_order_id":"388043193025","statement_descriptor":"WWW.MERCADOPAGO.COM","cardholder":{"name":"APRO","identification":{"number":"31234595","type":"DNI"}},"authorization_code":"null","marketplace_fee":0,"deduction_schema":null,"refunds":[],"amount_refunded":0,"last_modified_by_admin":null,"api_version":"1","concept_id":null,"concept_amount":0,"internal_metadata":{},"collector":{"id":239130440,"first_name":"Maxi","last_name":"Britez","phone":{"area_code":" ","number":"1149758652","extension":""},"email":"maxi.britez@redb.ee","nickname":"BRMA3918282"}}}}'

        MercadoPagoIPNPush pushData = mapper.readValue(paymentObject, MercadoPagoIPNPush.class);
        then:
        pushData
    }
}
