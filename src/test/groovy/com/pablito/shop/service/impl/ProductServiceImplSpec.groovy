package com.pablito.shop.service.impl

import com.pablito.shop.config.properties.FilePropertiesConfig
import com.pablito.shop.domain.dao.Product
import com.pablito.shop.helper.FileHelper
import com.pablito.shop.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Path

class ProductServiceImplSpec extends Specification {

    def filePropertiesConfig = Mock(FilePropertiesConfig)
    def productRepository = Mock(ProductRepository)
    def fileHelper = Mock(FileHelper)

    def productService = new ProductServiceImpl(filePropertiesConfig, productRepository, fileHelper)

    def 'should delete product'() {
        given:
        def id = 1L

        when:
        productService.delete(id)

        then:
        1 * productRepository.deleteById(id)
        0 * _

    }

    def 'should get user by id'() {
        given:
        def id = 1L

        when:
        productService.getProductById(id)

        then:
        1 * productRepository.getById(id)
        0 * _

    }

    def 'should get Page'() {
        given:
        def page = PageRequest.of(1, 1)

        when:
        productService.getPage(page)

        then:
        1 * productRepository.findAll(page)
        0 * _
    }

    def 'should save Product'() {
        given:
        def product = new Product(name: 'pomidor', code: 'XYZ', price: 2.22, quantity: 100L, imagePath: "asdf/asfasf")
        def image = Mock(MultipartFile)
        def newFileName = "pliczek.gif"
        def pathToFolder = "abcd/user/abcd"
        def inputStream = Mock(InputStream)


        when:
        productService.save(product, image)

        then:
        1 * filePropertiesConfig.getProduct() >> pathToFolder
        1 * image.getOriginalFilename() >> newFileName
        1 * image.getInputStream() >> inputStream
        1 * fileHelper.saveFile(inputStream, _ as Path /*coś co jest Pathem*/)
        1 * fileHelper.deleteFile(null)
        1 * productRepository.save(product)
        0 * _

    }

    def 'should update Product'() {
        given:
        def id = 1L
        def image = Mock(MultipartFile)
        def newFileName = "pliczek.gif"
        def pathToFolder = "abcd/user/abcd"
        def inputStream = Mock(InputStream)
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def productDb = new Product(name: 'pomodoroZ', code: 'XYZ13', price: 11.00, quantity: 1000L, imagePath: "kasablanka/marakesz")

        when:
        productService.update(product, id, image)

        then:
        1 * productRepository.getById(id) >> productDb
        1 * filePropertiesConfig.getProduct() >> pathToFolder
        1 * image.getOriginalFilename() >> newFileName
        1 * image.getInputStream() >> inputStream
        1 * fileHelper.saveFile(inputStream, _ as Path)
        1 * fileHelper.deleteFile(productDb.getImagePath())
        0 * _
    }
}
