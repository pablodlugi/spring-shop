package com.pablito.shop.validator.impl

import com.pablito.shop.domain.dto.UserDto
import spock.lang.Specification


class PasswordValidatorSpec extends Specification {
    def passwordValidator = new PasswordValidator()
    //PasswordValidator passwordValidator = new PasswordValidator() - też można

    def 'should valid password validator'() {
        given:
        def userDto = new UserDto(password: password, confirmedPassword: confirmedPassword)

        when:
        def result = passwordValidator.isValid(userDto, null)

        then:
        result == expected

        where:
        password | confirmedPassword || expected
        'sfora'  | 'sfora'           || true
        'sfora'  | 'nie sfora'       || false
    }


}
