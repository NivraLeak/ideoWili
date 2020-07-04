package com.studydesk.Service;

import com.studydesk.Model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DocumentService {
    Page<Document> getAllDocumentsByCategoryId(Long categoryId, Pageable pageable);
    Document getDocumentByIdAndCategoryId(Long categoryId, Long documentId);
    Document createDocument(Long categoryId,Document documentId);
    Document updateDocument(Long categoryId,Long documentId, Document documentDetails);
    ResponseEntity<?> deleteDocument(Long categoryId, Long documentId);
    List<Document> getAllDocuments();
}
