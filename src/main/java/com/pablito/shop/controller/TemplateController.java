package com.pablito.shop.controller;

import com.pablito.shop.domain.dao.Template;
import com.pablito.shop.domain.dto.TemplateDto;
import com.pablito.shop.mapper.TemplateMapper;
import com.pablito.shop.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN"))
    public TemplateDto saveTemplate(@RequestBody @Valid TemplateDto templateDto) {
        return templateMapper.toDto(templateService.saveTemplate(templateMapper.toDao(templateDto)));
    }

    @PutMapping
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN"))
    public void updateTemplate(@RequestBody @Valid TemplateDto templateDto) {
        templateService.updateTemplate(templateMapper.toDao(templateDto), templateDto.getName());
    }

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN"))
    public TemplateDto getTemplateByName(@RequestParam String name) {
        return templateMapper.toDto(templateService.getTemplateByName(name));
    }

    @DeleteMapping("{id}")
    @Operation(security = @SecurityRequirement(name = "BEARER AUT TOKEN"))
    public void deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
    }
}
