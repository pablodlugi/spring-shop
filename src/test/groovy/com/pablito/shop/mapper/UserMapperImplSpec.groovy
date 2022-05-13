package com.pablito.shop.mapper

import com.pablito.shop.domain.dao.User
import com.pablito.shop.domain.dto.UserDto
import com.pablito.shop.mapper.UserMapperImpl
import spock.lang.Specification

class UserMapperImplSpec extends Specification {

    def userMapper = new UserMapperImpl()

    def 'should map user to userDto'() {
        given:
        def user = new User(id: 1, username: 'pablo13', password: 'mascarpone', email: 'kapusta@gmail', firstName: 'Pablo', lastName: 'Gonzalez')

        when:
        def result = userMapper.toDto(user)

        then:
        result == new UserDto(id: 1, username: 'pablo13', email: 'kapusta@gmail', firstName: 'Pablo', lastName: 'Gonzalez')
    }

    def 'should map userDto to user'() {
        given:
        def userDto = new UserDto(id: 1, username: 'pablo13', password: 'mascarpone', email: 'kapusta@gmail', firstName: 'Pablo', lastName: 'Gonzalez')

        when:
        def result = userMapper.toDao(userDto)

        then:
        result == new User(id: 1, username: 'pablo13', password: 'mascarpone', email: 'kapusta@gmail', firstName: 'Pablo', lastName: 'Gonzalez')
    }

    def 'should map null userDto too null'() {
        def userDto = null

        when:
        def result = userMapper.toDao(userDto)

        then:
        result == null
    }

    def 'should map null user too null'() {
        def user = null

        when:
        def result = userMapper.toDto(user)

        then:
        result == null
    }
}
