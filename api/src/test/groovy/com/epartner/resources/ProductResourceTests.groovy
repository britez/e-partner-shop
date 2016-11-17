package com.epartner.resources

import com.epartner.representations.ProductRepresentation
import com.epartner.services.ProductService
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
}
