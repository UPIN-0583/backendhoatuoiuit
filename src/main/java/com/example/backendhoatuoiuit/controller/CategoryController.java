package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.CategoryDTO;
import com.example.backendhoatuoiuit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/active")
    public List<CategoryDTO> getActiveCategories() {
        return categoryService.getActiveCategories();
    }
}
