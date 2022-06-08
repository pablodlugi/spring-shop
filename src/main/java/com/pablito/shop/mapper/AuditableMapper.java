package com.pablito.shop.mapper;

import com.pablito.shop.domain.dao.Auditable;
import com.pablito.shop.domain.dto.AuditableDto;
import com.pablito.shop.security.SecurityUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface AuditableMapper<DAO extends Auditable, DTO extends AuditableDto> {

    @AfterMapping
    default void mapAuditable(DAO dao, @MappingTarget AuditableDto.AuditableDtoBuilder<?, ?> dto) {
        if(!SecurityUtils.hasRole("ROLE_ADMIN")) {
            dto.createdDate(null);
            dto.createdBy(null);
            dto.lastModifiedBy(null);
            dto.lastModifiedDate(null);
        }
    }

}
