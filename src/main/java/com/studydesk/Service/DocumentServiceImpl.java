package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Document;
import com.studydesk.repository.CategoryRepository;
import com.studydesk.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService{
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Page<Document> getAllDocumentsByCategoryId(Long categoryId, Pageable pageable) {
        return documentRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Document getDocumentByIdAndCategoryId(Long categoryId, Long documentId) {
        return documentRepository.findByIdAndCategoryId(documentId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Topic not found with Id" + documentId +
                                " ans CourseId " + categoryId
                ));
    }

    @Override
    public Document createDocument(Long categoryId, Document documentId) {
        return categoryRepository.findById(categoryId).map(category -> {
            documentId.setCategory(category);
            return documentRepository.save(documentId);
        }).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
    }

    @Override
    public Document updateDocument(Long categoryId, Long documentId, Document documentDetails) {
        if(!categoryRepository.existsById(categoryId))
            throw  new ResourceNotFoundException("Category", "Id", categoryId);

        return documentRepository.findById(documentId).map(topic -> {
            topic.setName(documentDetails.getName());
            topic.setType(documentDetails.getType());
            topic.setDescription(documentDetails.getDescription());
            return documentRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("Document", "Id", documentId));
    }

    @Override
    public ResponseEntity<?> deleteDocument(Long categoryId, Long documentId) {
        return documentRepository.findByIdAndCategoryId(documentId, categoryId).map(document -> {
            documentRepository.delete(document);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                " Topic not found with Id " + documentId + " and CourseId " + categoryId
        ));
    }


    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

}
