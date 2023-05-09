package com.trabalhoRoberto.demo.repositories;

import com.trabalhoRoberto.demo.entities.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("When find a category for ID")
    public void shouldFindCategoryById() {
        Category category = new Category();
        category.setName("Category Test");

        entityManager.persist(category);
        entityManager.flush();

        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        Assertions.assertTrue(foundCategory.isPresent());
        Assertions.assertEquals(category.getName(), foundCategory.get().getName());
    }

    @Test
    @DisplayName("When find all the categories")
    public void shouldFindAllCategories() {
        Category category1 = new Category();
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setName("Category 2");

        Category category3 = new Category();
        category3.setName("Category 3");

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);
        entityManager.flush();

        List<Category> categories = categoryRepository.findAll();

        Assertions.assertEquals(3, categories.size());
        Assertions.assertEquals(category1.getName(), categories.get(0).getName());
        Assertions.assertEquals(category2.getName(), categories.get(1).getName());
        Assertions.assertEquals(category3.getName(), categories.get(2).getName());
    }

    @Test
    @DisplayName("When to save a new category")
    public void shouldSaveCategory() {
        Category category = new Category();
        category.setName("New Category");

        Category savedCategory = categoryRepository.save(category);

        Assertions.assertNotNull(savedCategory.getId());
        Assertions.assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    @DisplayName("When to delete a category")
    public void shouldDeleteCategory() {
        Category category = new Category();
        category.setName("Category Test");

        entityManager.persist(category);
        entityManager.flush();

        categoryRepository.delete(category);

        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
        Assertions.assertFalse(foundCategory.isPresent());
    }
}
