package com.epartner.services

import com.epartner.repositories.CategoryRepository
import com.epartner.representations.CategoryRepresentation
import com.epartner.resources.CategoryResourceTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

/**
 * Created by mbritez on 28/08/16.
 */
class CategoryServiceTests extends Specification {

    def CategoryService service
    def CategoryRepository mockedRepository

    def setup() {
        mockedRepository = Mock(CategoryRepository)
        this.service = new CategoryService(mockedRepository)
    }

    def "get all categories"(){
        setup:
        def mockedResult = new PageImpl([])

        when:
        def result = service.getAllCategories(Optional.of(1), Optional.of(1))

        then:
        1 * mockedRepository.findAll(_ as PageRequest) >> mockedResult
        result
    }

    def "create"(){
        setup:
        def mockedResult = Mock(CategoryRepresentation)
        def mockedCategory = Mock(com.epartner.domain.Category) {
            convert() >> mockedResult
        }
        def  mockedRepresentation = Mock(CategoryRepresentation) {
            convert() >> mockedCategory
        }

        when:
        def result = service.create(mockedRepresentation)

        then:
        1 * mockedRepository.save(mockedCategory) >> mockedCategory
        result
        result == mockedResult
    }

}
