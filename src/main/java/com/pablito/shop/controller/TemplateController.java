package com.pablito.shop.controller;

import com.pablito.shop.domain.dao.Template;
import com.pablito.shop.domain.dto.TemplateDto;
import com.pablito.shop.mapper.TemplateMapper;
import com.pablito.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    public TemplateDto saveTemplate(@RequestBody @Valid TemplateDto templateDto) {
        return templateMapper.toDto(templateService.saveTemplate(templateMapper.toDao(templateDto)));
    }

    @PutMapping
    public void updateTemplate(@RequestBody @Valid TemplateDto templateDto) {
        templateService.updateTemplate(templateMapper.toDao(templateDto), templateDto.getName());
    }

    @GetMapping
    public TemplateDto getTemplateByName(@RequestParam String name) {
        return templateMapper.toDto(templateService.getTemplateByName(name));
    }

    @DeleteMapping("{id}")
    public void deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
    }
}
