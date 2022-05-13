package com.pablito.shop.service.impl

import com.pablito.shop.domain.dao.Basket
import com.pablito.shop.domain.dao.Product
import com.pablito.shop.domain.dao.User
import com.pablito.shop.exception.QuantityExceededException
import com.pablito.shop.repository.BasketRepository
import com.pablito.shop.service.ProductService
import com.pablito.shop.service.UserService
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {

    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserService)
    def productService = Mock(ProductService)

    //def basketService = new BasketServiceImpl(basketRepository, userService, productService)
    def basketService = Spy(BasketServiceImpl, constructorArgs: [basketRepository, userService, productService])

    def 'should add product to existing basket'() {

        given:
        def productId = 1L
        def quantity = 5L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def basket = new Basket(id: 1L, user: currentUser, product: product, quantity: 2L)
        def optionalBasket = Optional.of(basket)

        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * basketService.addToBasket(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        1 * basketRepository.save(basket)
        0 * _
    }

    def 'should create basket and add product to basket'() {

        given:
        def productId = 1L
        def quantity = 5L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def basket = new Basket(user: currentUser, product: product, quantity: quantity)
        def optionalBasket = Optional.ofNullable(null)


        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * basketService.addToBasket(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        1 * productService.getProductById(productId) >> product
        1 * basketRepository.save(basket)
        0 * _
    }

    def 'should throw QuantityExceededException when basket exist'() {

        given:
        def productId = 1L
        def quantity = 1005L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def basket = new Basket(id: 1L, user: currentUser, product: product, quantity: 2L)
        def optionalBasket = Optional.of(basket)

        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * basketService.addToBasket(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        thrown QuantityExceededException
        0 * _

    }

    def 'should throw QuantityExceededException when basket doesnt exist'() {
        given:
        def productId = 1L
        def quantity = 1005L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def optionalBasket = Optional.ofNullable(null)

        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * basketService.addToBasket(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        1 * productService.getProductById(productId) >> product
        thrown QuantityExceededException
        0 * _
    }

    def 'should override Product Quantity'() {
        given:
        def productId = 1L
        def quantity = 5L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def basket = new Basket(id: 1L, user: currentUser, product: product, quantity: 2L)
        def optionalBasket = Optional.of(basket)

        when:
        basketService.overrideProductQuantity(productId, quantity)

        then:
        1 * basketService.overrideProductQuantity(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        0 * _
    }

    def 'should throw QuantityExceededException when override product Quantity'() {
        given:
        def productId = 1L
        def quantity = 1005L
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def basket = new Basket(id: 1L, user: currentUser, product: product, quantity: 2L)
        def optionalBasket = Optional.of(basket)

        when:
        basketService.overrideProductQuantity(productId, quantity)

        then:
        1 * basketService.overrideProductQuantity(productId, quantity)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserIdAndProductId(currentUser.getId(), productId) >> optionalBasket
        thrown QuantityExceededException
        0 * _
    }

    def 'should delete from basket'() {
        given:
        def productId = 1L
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')

        when:
        basketService.deleteFromBasket(productId)

        then:
        1 * basketService.deleteFromBasket(productId)
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.deleteByUserIdAndProductId(currentUser.getId(), productId)
        0 * _
    }

    def 'should clear basket'() {

        given:
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')

        when:
        basketService.clearBasket()

        then:
        1 * basketService.clearBasket()
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.deleteByUserId(currentUser.getId())
        0 * _

    }

    def 'should return products list'() {

        given:
        def currentUser = new User(id: 1L, username: 'pablito', email: 'abcd@interia.pl', firstName: 'Pawel', lastName: 'Kubica')
        def product = new Product(name: 'pomodoro', code: 'XYZ', price: 10.00, quantity: 1000L, imagePath: "asdf/asfasf")
        def product1 = new Product(name: 'ogorek', code: 'XYZ1', price: 10.00, quantity: 1000L, imagePath: "asfsa/asfasf")
        def basket = new Basket(id: 1L, user: currentUser, product: product, quantity: 2L)
        def basket1 = new Basket(id: 1L, user: currentUser, product: product1, quantity: 2L)
        var basketEntries = [basket, basket1] as ArrayList
        var productsResultList = [product, product1] as ArrayList

        when:
        def result = basketService.getAllProductsFromBasket()

        then:
        1 * basketService.getAllProductsFromBasket()
        1 * userService.getCurrentUser() >> currentUser
        1 * basketRepository.findByUserId(currentUser.getId()) >> basketEntries
        result == productsResultList
        0 * _
    }

}
