package com.pablito.shop.flyweight.generic.strategy.mail;

import com.pablito.shop.flyweight.generic.strategy.GenericStrategy;
import com.pablito.shop.flyweight.model.MailType;

public interface MailGeneratorStrategy extends GenericStrategy<MailType> {
    String generateMail();
}
