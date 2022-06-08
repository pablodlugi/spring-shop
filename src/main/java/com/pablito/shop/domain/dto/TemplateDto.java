package com.pablito.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class TemplateDto extends AuditableDto {
    private Long id;

    @NotBlank
    private String name;

    private String subject;

    @NotBlank
    private String body;
}
