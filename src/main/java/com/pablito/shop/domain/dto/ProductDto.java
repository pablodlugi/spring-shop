package com.pablito.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank(message = "Product code cannot be null")
    private String code;

    @NotNull
    private Double price;

    @NotNull
    private Long quantity;

    private Integer revisionNumber;

    private String imagePath;
}
