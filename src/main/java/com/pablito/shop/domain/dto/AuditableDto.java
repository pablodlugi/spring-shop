package com.pablito.shop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AuditableDto {

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;
}
