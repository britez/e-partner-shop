package com.epartner.resources

import com.epartner.representations.CategoryRepresentation
import com.epartner.services.CategoryService
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

/**
 * Created by mbritez on 28/08/16.
 */
class CategoryResourceTest extends Specification {

    private CategoryResource resource
    private CategoryService mockedService

    def setup(){
        mockedService = Mock(CategoryService)
        resource = new CategoryResource(mockedService)
    }

    def "get all Categories"(){

        setup:
        def mockedResult = new PageImpl([])
        def max = 10
        def page = 0

        when:
        def result = resource.list(max, page)

        then:
        1 * mockedService.getAllCategories(_ as Optional, _ as Optional) >> mockedResult
        result
        result == mockedResult
    }


    def "create"(){
        setup:
        def mockedRepresentation = Mock(CategoryRepresentation)

        when:
        def result = resource.create(mockedRepresentation)

        then:
        1 * mockedService.create(mockedRepresentation) >> mockedRepresentation
        result
        result == mockedRepresentation
    }

}
