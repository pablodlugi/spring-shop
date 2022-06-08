package com.pablito.shop.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pablito.shop.domain.dto.LoginDto;
import com.pablito.shop.domain.dto.TokenDto;
import com.pablito.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        /*
        JJWT IMPLEMENTATION
         */
//        Claims claims = new DefaultClaims()
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
//                .setSubject(authentication.getName());
//
//        claims.put("authorities", authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(",")));
//
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, "masakracja")
//                .compact();


        /*
        JAVA JWT
         */
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .withSubject(authentication.getName())
                .withClaim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .sign(Algorithm.HMAC512("masakracja"));

        return new TokenDto(token);
    }
}
