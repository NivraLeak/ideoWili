package com.studydesk.Controller;

import com.studydesk.Model.Category;
import com.studydesk.Resource.CategoryResource;
import com.studydesk.Resource.SaveCategoryResource;
import com.studydesk.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryController {

     @Autowired
     private ModelMapper mapper;
     @Autowired
     private CategoryService categoryService;

    @GetMapping("/tags")
    public Page<CategoryResource> getAllTags(Pageable pageable) {
        List<CategoryResource> tags = categoryService.getAllCategories(pageable).getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int tagsCount = tags.size();
        return new PageImpl<>(tags, pageable, tagsCount);
    }


    @GetMapping("/tags/{id}")
    public CategoryResource getTagById(@PathVariable(name = "id") Long tagId) {
        return convertToResource(categoryService.getCategoryById(tagId));
    }

    @PostMapping("/tags")
    public CategoryResource createTag(@Valid @RequestBody SaveCategoryResource resource) {
        return convertToResource(categoryService.createCategory(convertToEntity(resource)));
    }
    @PutMapping("/tags/{id}")
    public CategoryResource updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody SaveCategoryResource resource) {
        return convertToResource(categoryService.updateCategory(tagId, convertToEntity(resource)));
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long tagId) {
        return categoryService.deleteCategory(tagId);
    }

    private Category convertToEntity(SaveCategoryResource resource) {
        return mapper.map(resource, Category.class);
    }

    private CategoryResource convertToResource(Category entity) {
        return mapper.map(entity, CategoryResource.class);
    }





}
