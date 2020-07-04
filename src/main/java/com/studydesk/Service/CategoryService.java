package com.studydesk.Service;

import com.studydesk.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    Page<Category> getAllCategories(Pageable pageable);
    //Page<Tag> getAllTagsByPostId(Long postId, Pageable pageable);
    Category getCategoryById(Long categoryId);
    Category createCategory(Category category);
    Category updateCategory(Long categoryId, Category tagDetails);
    ResponseEntity<?> deleteCategory(Long categoryId);


}
