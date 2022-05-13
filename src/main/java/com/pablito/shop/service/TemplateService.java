package com.pablito.shop.service;

import com.pablito.shop.domain.dao.Template;
import com.pablito.shop.domain.dto.TemplateDto;


public interface TemplateService {

    Template getTemplateByName(String name);

    Template saveTemplate(Template template);

    void deleteTemplate(Long id);

    Template updateTemplate(Template template, String name);

}
