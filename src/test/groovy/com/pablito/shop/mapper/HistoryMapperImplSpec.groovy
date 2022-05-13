package com.pablito.shop.mapper

import com.pablito.shop.domain.dao.Product
import com.pablito.shop.domain.dao.User
import com.pablito.shop.mapper.HistoryMapperImpl
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import spock.lang.Specification

class HistoryMapperImplSpec extends Specification {

    def historyMapper = new HistoryMapperImpl()

    def 'should map revision to userDto'() {
        given:
        def user = new User(id: 1, username: 'pablo13', password: 'mascarpone',
                email: 'kapusta@gmail', firstName: 'Pablo', lastName: 'Gonzalez')
        def revision =
                Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity()), user)

        when:
        def result = historyMapper.mapToUserDto(revision)

        then:
        result.id == user.id
        result.username == user.username
        result.password == null
        result.email == user.email
        result.firstName == user.firstName
        result.lastName == user.lastName
    }

    def 'should map revision to null'() {
        given:
        def revision = null

        when:
        def result = historyMapper.mapToUserDto(revision)
        def result1 = historyMapper.mapToProductDto(revision)

        then:
        result == null
        result1 == null
    }

    def 'should map revision to productDto'() {
        given:
        def product = new Product(id: 1, name: 'pomodoro', code: 'XYZ',
                price: 10.00, quantity: 1000L)
        def revision =
                Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity()), product)

        when:
        def result = historyMapper.mapToProductDto(revision)

        then:
        result.id == product.id
        result.name == product.name
        result.code == product.code
        result.price == product.price
        result.quantity == product.quantity

    }
}
