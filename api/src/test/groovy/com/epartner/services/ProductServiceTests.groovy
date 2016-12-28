package com.epartner.services

import com.epartner.converters.ProductConverter
import com.epartner.converters.TechnicalSpecificationConverter
import com.epartner.domain.Category
import com.epartner.domain.Product
import com.epartner.repositories.CategoryRepository
import com.epartner.repositories.ProductRepository
import com.epartner.representations.CategoryRepresentation
import com.epartner.representations.ProductRepresentation
import org.junit.Ignore
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

/**
 * Created by mbritez on 3/09/16.
 */
@Ignore
class ProductServiceTests extends Specification {

    private ProductService service
    private ProductRepository mockedRepo
    private ProductConverter mockedConverter
    private CategoryService mockedCategoryService
    private TechnicalSpecificationConverter techConverter
    private StorageService mockedStorageService
    private CategoryRepository mockedCategoryRepository

    def setup() {
        mockedCategoryService = Mock(CategoryService)
        mockedConverter = Mock(ProductConverter)
        mockedRepo = Mock(ProductRepository)
        techConverter = Mock(TechnicalSpecificationConverter)
        mockedStorageService = Mock(StorageService)
        mockedCategoryRepository = Mock(CategoryRepository)

        service = new ProductService(
                mockedRepo,
                mockedConverter,
                techConverter,
                mockedCategoryService,
                mockedStorageService,
                mockedCategoryRepository)
    }

    def "test create"() {
        setup:
        def mockedCategoryRepresentation = Mock(CategoryRepresentation) {
            getId() >> 1L
        }
        def mockedRepresentation = Mock(ProductRepresentation) {
            getCategory() >> mockedCategoryRepresentation
        }
        def mockedProduct = Mock(Product)
        when:
        def result = service.create(mockedRepresentation)
        then:
        1 * mockedCategoryService.show(1L) >> mockedCategoryRepresentation
        1 * mockedConverter.convert(mockedRepresentation) >> mockedProduct
        1 * mockedRepo.save(mockedProduct) >> mockedProduct
        1 * mockedConverter.convert(mockedProduct) >> mockedRepresentation
        result
        result == mockedRepresentation
    }

    def "test update"() {
        setup:
        def mockedRepresentation = Mock(ProductRepresentation)
        def mockedProduct = Mock(Product)
        def mockedCategory = Mock(CategoryRepresentation) {
            getId() >> 1L
        }
        def mockedStoredCategory = Mock(Category)

        when:
        def result = service.update(mockedRepresentation, 1L)
        then:
        1 * mockedRepo.findOne(1L) >> mockedProduct
        1 * mockedRepo.save(mockedProduct)
        1 * mockedConverter.convert(mockedProduct) >> mockedRepresentation
        1 * mockedCategoryRepository.findOne(1L) >> mockedStoredCategory
        1 * mockedRepresentation.getDescription()
        1 * mockedRepresentation.getName()
        1 * mockedRepresentation.getCategory() >> mockedCategory
        result
        result == mockedRepresentation
    }

    def "test update with wrong id"() {
        setup:
        def mockedRepresentation = Mock(ProductRepresentation)
        when:
        def result = service.update(mockedRepresentation, 1L)
        then:
        1 * mockedRepo.findOne(1L) >> { throw Mock(EntityNotFoundException) }
        !result
        thrown(EntityNotFoundException)
    }

    def "test show"() {
        setup:
        def mockedProduct = Mock(Product)
        def mockedResult = Mock(ProductRepresentation)
        when:
        def result = service.show(1L)
        then:
        1 * mockedRepo.findOne(1L) >> mockedProduct
        1 * mockedConverter.convert(mockedProduct) >> mockedResult
        result
        result == mockedResult
    }

    def "test delete"() {
        setup:
        def mockedProduct = Mock(Product)
        when:
        service.delete(1L)
        then:
        1 * mockedRepo.findOne(1L) >> mockedProduct
        1 * mockedRepo.delete(mockedProduct)
    }

    def "test list"() {
        setup:
        def mockedProduct = Mock(Product)
        def mockedProductRepresentation = Mock(ProductRepresentation)
        def mockedResult = new PageImpl([mockedProduct])
        def mockedResponse = new PageImpl([mockedProductRepresentation])
        when:
        def result = service.list(Optional.of(""), Optional.of(10), Optional.of(0))
        then:
        1 * mockedRepo.findAllByNameContainingOrDescriptionContaining(_ as String, _ as String,  _ as PageRequest) >> mockedResult
        1 * mockedConverter.convert(mockedResult, _ as Pageable) >> mockedResponse
        result
        def product = result.getContent().get(0)
        product
        product == mockedProductRepresentation
    }
}
