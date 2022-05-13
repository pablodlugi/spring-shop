package com.pablito.shop.mapper;

import com.pablito.shop.domain.dao.Template;
import com.pablito.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template toDao(TemplateDto templateDto);

    TemplateDto toDto(Template template);
}
