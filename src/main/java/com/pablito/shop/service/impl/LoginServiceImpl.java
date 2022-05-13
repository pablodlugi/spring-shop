package com.pablito.shop.service.impl;

import com.pablito.shop.domain.dto.LoginDto;
import com.pablito.shop.domain.dto.TokenDto;
import com.pablito.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDto login(LoginDto loginDto) {
        return null;
    }
}
