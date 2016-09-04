package com.epartner.services

import com.epartner.exceptions.StorageException
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

/**
 * Created by maty on 4/9/16.
 */
class StorageServiceTests extends Specification{

    def baseTestPath = "/private/tmp"
    def imageService = new StorageService(baseTestPath)

    def "que un archivo se guarde"(){

        setup:
            def mockedFileName = "test.txt"
            def mockedMultiPart = new MockMultipartFile("file",mockedFileName , "text/plain", "Spring Framework".getBytes())

        when:
            def fileName = imageService.store(mockedMultiPart)

        then:
           fileName != null
          new File(baseTestPath +"/" + fileName).exists()

        cleanup:
            new File(baseTestPath +"/" + fileName).delete()


    }

    def "no se puede crear la imagen"() {

        when:
        imageService.store(new MockMultipartFile("file","pepe.txt" , "text/plain", "".getBytes()))

        then:
            thrown(StorageException)

    }
}
