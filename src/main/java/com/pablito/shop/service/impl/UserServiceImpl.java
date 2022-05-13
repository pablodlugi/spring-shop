package com.pablito.shop.service.impl;

import com.pablito.shop.domain.dao.User;
import com.pablito.shop.repository.RoleRepository;
import com.pablito.shop.repository.UserRepository;
import com.pablito.shop.security.SecurityUtils;
import com.pablito.shop.service.MailService;
import com.pablito.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.rmi.server.UID;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor //wieloargumentowy konstruktor dla finalnych zmiennych
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;

    @Override
    public User save(User user) {
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singleton(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var token = UUID.randomUUID().toString();
        user.setToken(token);
        var savedUser = userRepository.save(user);
        var variables = new HashMap<String, Object>();
        variables.put("url", "http://localhost:8080/api/users/confirm?token=" + token);
        mailService.send(user.getEmail(), "NEW_USER_WELCOME", variables, null, null);
        return savedUser;
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        var userDb = getUserById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setUsername(user.getUsername());
        userDb.setEmail(user.getEmail());
        return userDb;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void confirmUser(String token) {
        var user = userRepository.findByToken(token).orElseThrow(EntityNotFoundException::new);
        user.setToken(null);
    }


}
