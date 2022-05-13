package com.pablito.shop.service;

import com.pablito.shop.domain.dto.LoginDto;
import com.pablito.shop.domain.dto.TokenDto;

public interface LoginService {

    TokenDto login(LoginDto loginDto);

}
