package org.example.controller;

import org.example.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Import(SecurityConfig.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    // GET-test
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnListOfProducts() throws Exception {
        Product p = new Product();
        p.setName("Testproduct");
        p.setSku("TP-001");
        p.setQuantity(10);

        when(productRepository.findAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Testproduct"));
    }

    // POST-test
    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateProduct() throws Exception {
        // Create JSON-string that simulate POST product.
        String json = """
                           { 
                           "name": "NewProduct",
                           "sku": "NP-001",
                           "quantity": 20
                }
                """;
        //Create product object that we expect back
        Product p = new Product();
        p.setName("NewProduct");
        p.setSku("NP-001");
        p.setQuantity(20);

        // Mock repository, should return product
        when(productRepository.save(any(Product.class))).thenReturn(p);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewProduct"));
    }
}