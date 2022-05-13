package com.pablito.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablito.shop.domain.dao.Product;
import com.pablito.shop.domain.dto.ProductDto;
import com.pablito.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindProductById() throws Exception {

        Product product = productRepository.save(Product.builder()
                .name("LAPTOP")
                .code("LAP13")
                .price(3000.0)
                .quantity(100L)
                .imagePath("url")
                .build());

        mockMvc.perform(get("/api/v1/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("LAPTOP"))
                .andExpect(jsonPath("$.code").value("LAP13"))
                .andExpect(jsonPath("$.price").value(3000.0))
                .andExpect(jsonPath("$.quantity").value(100))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.imagePath").value("url"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSaveProduct() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile("image", "obrazek.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        MockMultipartFile product = new MockMultipartFile("product", null, MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("LAPTOP")
                .code("LAP13")
                .price(3000.0)
                .quantity(100L)
                .build()));

        /*
        Mockujemu request z plikiem i JSONEM i w procesorze ustawiamy że będzie to metoda POST
         */
        mockMvc.perform(multipart("/api/v1/products")
                        .file(image1)
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("LAPTOP"))
                .andExpect(jsonPath("$.code").value("LAP13"))
                .andExpect(jsonPath("$.price").value(3000.0))
                .andExpect(jsonPath("$.quantity").value(100L))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.imagePath").value("target\\LAPTOP.jpg"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotSaveProductWhenFileExtensionIsNotSupported() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile("image", "obrazek.xyz", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        MockMultipartFile product = new MockMultipartFile("product", null, MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("LAPTOP")
                .code("LAP13")
                .price(3000.0)
                .quantity(100L)
                .build()));

        /*
        Mockujemu request z plikiem i JSONEM i w procesorze ustawiamy że będzie to metoda POST
         */
        mockMvc.perform(multipart("/api/v1/products")
                        .file(image1)
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("saveProduct.image: File extension not supported"));
    }

}
