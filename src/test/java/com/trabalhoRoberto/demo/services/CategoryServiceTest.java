package com.trabalhoRoberto.demo.services;

import com.trabalhoRoberto.demo.entities.Category;
import com.trabalhoRoberto.demo.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Category category = new Category();
        category.setName("Categoria de teste");
        category.setFamily("Família de teste");
        category.setGroup("Grupo de teste");

        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        Category savedCategory = categoryService.save(category);

        assertEquals(category, savedCategory);
        verify(categoryRepository, times(1)).saveAndFlush(category);
    }

    @Test
    void testFindAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Categoria 1"));
        categories.add(new Category(2, "Categoria 2"));
        categories.add(new Category(3, "Categoria 3"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.findAll();

        assertEquals(categories, retrievedCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria de teste");
        category.setFamily("Família de teste");
        category.setGroup("Grupo de teste");

        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        Category updatedCategory = categoryService.update(category);

        assertEquals(category, updatedCategory);
        verify(categoryRepository, times(1)).saveAndFlush(category);
    }

    @Test
    void testFindById() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria de teste");
        category.setFamily("Família de teste");
        category.setGroup("Grupo de teste");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Optional<Category> retrievedCategory = categoryService.findById(1);

        assertTrue(retrievedCategory.isPresent());
        assertEquals(category, retrievedCategory.get());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void testDelete() {
        Category category = new Category();
        category.setId(1);
        category.setName("Categoria de teste");
        category.setFamily("Família de teste");
        category.setGroup("Grupo de teste");

        categoryService.delete(category);

        verify(categoryRepository, times(1)).delete(category);
    }
}
