package com.epartner.converters

import com.epartner.representations.CategoryRepresentation
import org.springframework.data.domain.PageImpl
import spock.lang.Specification
import com.epartner.domain.Category

/**
 * Created by mbritez on 29/08/16.
 */
class CategoryConverterTests extends Specification {

    private CategoryConverter converter

    def setup(){
        converter = new CategoryConverter()
    }

    def "test convert to page impl"() {
        setup:
        def mockedCategory = Mock(Category) {
            getName() >> "aName"
            getDescription() >> "aDescription"
        }
        def page = new PageImpl([mockedCategory])

        when:
        def result = converter.convert(page)

        then:
        result
        result.getContent()
        def category = result.getContent()[0]
        category
        category.getName() == "aName"
        category.getDescription() == "aDescription"
    }

    def "test convert to representation"(){
        setup:
        def mockedRepresentation = Mock(CategoryRepresentation){
            getName() >> "aName"
            getDescription() >> "aDescription"
        }

        when:
        def result = converter.convert(mockedRepresentation)

        then:
        result
        result.getName() == mockedRepresentation.getName()
        result.getDescription() == mockedRepresentation.getDescription()
    }
}
