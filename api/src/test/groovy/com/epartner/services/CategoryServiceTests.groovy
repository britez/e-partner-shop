package com.epartner.services

import com.epartner.converters.CategoryConverter
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
    def CategoryConverter mockedConverter

    def setup() {
        mockedRepository = Mock(CategoryRepository)
        mockedConverter = Mock(CategoryConverter)
        this.service = new CategoryService(mockedRepository, mockedConverter)
    }

    def "get all categories"(){
        setup:
        def mockedResult = new PageImpl([])
        def storedPage = new PageImpl([])

        when:
        def result = service.getAllCategories(Optional.of(1), Optional.of(1))

        then:
        1 * mockedRepository.findAll(_ as PageRequest) >> storedPage
        1 * mockedConverter.convert(storedPage) >> mockedResult
        result
        result == mockedResult
    }

    def "create"(){
        setup:
        def mockedResult = Mock(CategoryRepresentation)
        def mockedCategory = Mock(com.epartner.domain.Category)
        def mockedRepresentation = Mock(CategoryRepresentation)

        when:
        def result = service.create(mockedRepresentation)

        then:
        1 * mockedRepository.save(mockedCategory) >> mockedCategory
        1 * mockedConverter.convert(mockedRepresentation) >> mockedCategory
        1 * mockedConverter.convert(mockedCategory) >> mockedResult
        result
        result == mockedResult
    }

    def "update"() {
        setup:
        def mockedRepresentation = Mock(CategoryRepresentation)
        def mockedStored = Mock(com.epartner.domain.Category)
        when:
        def result = service.update(mockedRepresentation, 1L)
        then:
        1 * mockedRepository.findOne(1L) >> mockedStored
        1 * mockedRepository.save(mockedStored) >> mockedStored
        1 * mockedConverter.convert(mockedStored) >> mockedRepresentation
        result
        result == mockedRepresentation
    }

    def "delete"() {
        setup:
        def mockedStored = Mock(com.epartner.domain.Category)
        when:
        service.delete(1L)
        then:
        1 * mockedRepository.findOne(1L) >> mockedStored
        1 * mockedRepository.delete(mockedStored)
    }

}
