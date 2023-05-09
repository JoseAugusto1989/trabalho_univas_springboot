package com.trabalhoRoberto.demo.services;

import com.trabalhoRoberto.demo.entities.Category;
import com.trabalhoRoberto.demo.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    @DisplayName("Deve salvar a categoria")
    void shouldSaveCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria Teste");

        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.save(category);

        Assertions.assertEquals(category, savedCategory);
    }

    @Test
    @DisplayName("Deve retornar todas as categorias")
    void shouldReturnAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Categoria 1"));
        categories.add(new Category(2, "Categoria 2"));
        categories.add(new Category(3, "Categoria 3"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> returnedCategories = categoryService.findAll();

        Assertions.assertEquals(categories, returnedCategories);
    }

    @Test
    @DisplayName("Deve atualizar a categoria")
    void shouldUpdateCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria Teste");

        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.update(category);

        Assertions.assertEquals(category, updatedCategory);
    }

    @Test
    @DisplayName("Deve retornar a categoria pelo ID")
    void shouldReturnCategoryById() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria Teste");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Optional<Category> returnedCategory = categoryService.findById(1);

        Assertions.assertEquals(category, returnedCategory.get());
    }

    @Test
    @DisplayName("Deve deletar a categoria")
    void shouldDeleteCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria Teste");

        categoryService.delete(category);

        Assertions.assertTrue(true);
    }
}
