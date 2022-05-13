package com.pablito.shop.service.impl

import com.pablito.shop.domain.dao.Role
import com.pablito.shop.domain.dao.User
import com.pablito.shop.repository.RoleRepository
import com.pablito.shop.repository.UserRepository
import com.pablito.shop.service.MailService
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class UserServiceImplSpec extends Specification {

    def passwordEncoder = Mock(PasswordEncoder)
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def mailService = Mock(MailService);

    def userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository, mailService)

    def 'should delete user'() {
        given:
        def id = 1L

        when:
        userService.delete(id)

        then:
        1 * userRepository.deleteById(id)
        0 * _
    }

    def 'should get user by id'() {
        given:
        def id = 1L

        when:
        userService.getUserById(id)

        then:
        1 * userRepository.getById(id)
        0 * _
    }

    def 'should get Page'() {
        given:
        def page = PageRequest.of(1, 1)

        when:
        userService.getPage(page)

        then:
        1 * userRepository.findAll(page)
        0 * _
    }

    def 'should save User'() {
        given:
        def user = Mock(User)
        def role = new Role()
        def passwd = "spock"
        def encodePasswd = "!@#%^&Q(*&^)"
        def email = "abcd@interia.pl"

        when:
        userService.save(user)

        then:
        1 * roleRepository.findByName("ROLE_USER") >> Optional.of(role)
        1 * user.setRoles(Collections.singleton(role))
        1 * user.getPassword() >> passwd
        1 * passwordEncoder.encode(passwd) >> encodePasswd
        1 * user.setToken(_ as String)
        1 * user.setPassword(encodePasswd)
        1 * userRepository.save(user)
        1 * user.getEmail() >> email
        1 * mailService.send(email, "NEW_USER_WELCOME", _ as HashMap, null, null)
        0 * _
    }

    def 'should update User'() {
        given:
        def id = 1L
        def user = new User(username: 'kaszalot', firstName: 'kamil', lastName: 'kaszalot', email: 'walen@onet.pl')
        def userDb = new User(username: 'wieloryb', firstName: 'kamil', lastName: 'kaszalot', email: 'humbak@onet.pl')

        when:
        userService.update(user, id)

        then:
        1 * userRepository.getById(id) >> userDb
        userDb == user
        0 * _
    }

    def 'should get current User'() {
        given:
        def authentication = Mock(Authentication)
        def securityContext = Mock(SecurityContext)
        def email = "rower@op.pl"
        def user = new User()
        SecurityContextHolder.setContext(securityContext)

        when:
        userService.getCurrentUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> email
        1 * userRepository.findByEmail(email) >> Optional.of(user)
        0 * _
    }

    def 'should throw EntityNotFoundException'() {
        given:
        def authentication = Mock(Authentication)
        def securityContext = Mock(SecurityContext)
        def email = "rower@op.pl"
        SecurityContextHolder.setContext(securityContext)
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> email
        1 * userRepository.findByEmail(email) >> Optional.empty()

        when:
        userService.getCurrentUser()

        then:
        thrown EntityNotFoundException

    }
}
