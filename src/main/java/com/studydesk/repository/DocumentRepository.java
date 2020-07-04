package com.studydesk.repository;

import com.studydesk.Model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByCategoryId(Long categoryId, Pageable pageable);
    Optional<Document> findByIdAndCategoryId(Long id, Long categoryId);
}
