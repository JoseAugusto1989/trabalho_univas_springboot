package com.trabalhoRoberto.demo.controllers;

import com.trabalhoRoberto.demo.dtos.CategoryDto;
import com.trabalhoRoberto.demo.entities.Category;
import com.trabalhoRoberto.demo.exceptions.CategoryException;
import com.trabalhoRoberto.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("")
    public ResponseEntity<Category> saveEquip(@Valid @RequestBody CategoryDto dto) throws CategoryException {
        var equip = new Category();
        BeanUtils.copyProperties(dto, equip);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(equip));
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> findAll() throws CategoryException {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Integer id) throws CategoryException {
        Optional<Category> optionalEquipment = service.findById(id);
        if (!optionalEquipment.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category not Found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalEquipment.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Integer id,
                                         @RequestBody @Valid CategoryDto dto) throws CategoryException {
        Optional<Category> optionalEquip = service.findById(id);
        if (!optionalEquip.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        var equip = new Category();
        BeanUtils.copyProperties(dto, equip);
        equip.setId(optionalEquip.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.save(equip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquip(@PathVariable(value = "id") Integer id) throws CategoryException {
        Optional<Category> newEquip = service.findById(id);
        if (!newEquip.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        service.delete(newEquip.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category deleted successfully");
    }

}
