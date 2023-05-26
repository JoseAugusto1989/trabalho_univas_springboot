package com.trabalhoRoberto.demo.services;

import com.trabalhoRoberto.demo.entities.Category;
import com.trabalhoRoberto.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public CategoryService(CategoryRepository categoryRepository) {
    }

    public Category save(Category category) {
        return repo.saveAndFlush(category);
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Category update(Category category) {
        return repo.saveAndFlush(category);
    }

    public Optional<Category> findById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public void delete(Category category) {
        repo.delete(category);
    }

}
