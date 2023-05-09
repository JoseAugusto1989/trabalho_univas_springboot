package com.trabalhoRoberto.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalhoRoberto.demo.dtos.CategoryDto;
import com.trabalhoRoberto.demo.entities.Category;
import com.trabalhoRoberto.demo.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService service;

    private Category category;

    @BeforeEach
    public void setUp() {
        category =

                new Category();
        category.

                setId(1);
        category.

                setName("Category 1");
    }

    @Test
    public void testSaveEquip() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setName("Category 1");
        Category category = new Category();
        category.setId(1);
        category.setName(dto.getName());

        when(service.save(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Category 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    public void testFindAll() throws Exception {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Category 2");

        List<Category> categories = Arrays.asList(category1, category2);

        when(service.findAll()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    public void getById_WithExistingId_ReturnsCategory() throws Exception {
        when(service.findById(1)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @Test
    public void getById_WithNonExistingId_ReturnsNotFound() throws Exception {
        when(service.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/category/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Category not Found!"));
    }

    @Test
    public void update_WithExistingIdAndValidDto_ReturnsNoContent() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setName("Updated Category");

        when(service.findById(1)).thenReturn(Optional.of(category));
        when(service.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void update_WithNonExistingId_ReturnsNotFound() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setName("Updated Category");

        when(service.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category not found"));
    }

    @Test
    public void deleteEquip_WithExistingId_ReturnsNoContent() throws Exception {
        when(service.findById(1)).thenReturn(Optional.of(category));

        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Category deleted successfully"));

        verify(service, times(1)).delete(category);
    }

    @Test
    public void deleteEquip_WithNonExistingId_ReturnsNotFound() throws Exception {
        when(service.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category not found"));
    }

}
