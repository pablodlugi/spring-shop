package com.pablito.shop.security;

import com.pablito.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    //abstrakcje wstrzykujemy
    public final UserService userService;

    public boolean hasAccessToUser(Long userId) {
        return userService.getCurrentUser().getId().equals(userId);
    }
}
