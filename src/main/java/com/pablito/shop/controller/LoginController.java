package com.pablito.shop.controller;

import com.pablito.shop.domain.dto.LoginDto;
import com.pablito.shop.domain.dto.TokenDto;
import com.pablito.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public TokenDto login(@RequestBody @Valid LoginDto login) {
        return loginService.login(login);
    }
}
