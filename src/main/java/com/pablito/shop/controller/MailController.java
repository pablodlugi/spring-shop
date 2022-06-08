package com.pablito.shop.controller;

import com.pablito.shop.flyweight.generic.GenericFactory;
import com.pablito.shop.flyweight.generic.strategy.mail.MailGeneratorStrategy;
import com.pablito.shop.flyweight.model.MailType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mails")
@RequiredArgsConstructor
public class MailController {

    private final GenericFactory<MailType, MailGeneratorStrategy> mailGenericFactory;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN"))
    public void generateGenericMail(@RequestParam MailType mailType) {
        mailGenericFactory.getStrategy(mailType).generateMail();
    }
}
