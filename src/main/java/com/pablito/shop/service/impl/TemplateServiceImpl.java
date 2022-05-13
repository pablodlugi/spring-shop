package com.pablito.shop.service.impl;

import com.pablito.shop.domain.dao.Template;
import com.pablito.shop.domain.dto.TemplateDto;
import com.pablito.shop.repository.TemplateRepository;
import com.pablito.shop.security.SecurityUtils;
import com.pablito.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public Template getTemplateByName(String name) {
        return templateRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Template saveTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Template updateTemplate(Template template, String name) {
        var templateDb = getTemplateByName(name);
        templateDb.setBody(template.getBody());
        templateDb.setName(template.getName());
        templateDb.setSubject(template.getSubject());
        return templateDb;
    }

}
