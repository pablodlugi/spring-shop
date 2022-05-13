package com.pablito.shop.flyweight.generic.strategy.mail.impl;

import com.pablito.shop.flyweight.generic.strategy.mail.MailGeneratorStrategy;
import com.pablito.shop.flyweight.model.MailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmationMailGenerator implements MailGeneratorStrategy {
    @Override
    public MailType getType() {
        return MailType.CONFIRMATION;
    }

    @Override
    public String generateMail() {
        log.info("Confirmation mail is generating (generic)");
        return null;
    }
}
