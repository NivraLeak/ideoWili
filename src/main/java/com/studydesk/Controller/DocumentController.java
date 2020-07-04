package com.studydesk.Controller;

import com.studydesk.Model.Document;
import com.studydesk.Model.Topíc;
import com.studydesk.Resource.DocumentResource;
import com.studydesk.Resource.SaveDocumentResource;
import com.studydesk.Resource.SaveTopicResource;
import com.studydesk.Resource.TopicResource;
import com.studydesk.Service.DocumentService;
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

@RestController
@RequestMapping("/api")
public class DocumentController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/categories/{categoryId}/documents")
    public Page<DocumentResource> getAllDocumentsByCategoryId(
            @PathVariable(name = "categoryId") Long categoryId,
            Pageable pageable)
    {
        Page<Document> documentPage = documentService.getAllDocumentsByCategoryId(categoryId, pageable);
        List<DocumentResource> resources = documentPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/categories/{categoryId}/documents/{documentId}")
    public DocumentResource getDocumentByIdAndCategoryId(@PathVariable(name = "categoryId") Long categoryId,
                                                 @PathVariable(name = "documentId") Long documentId){
        return convertToResource(documentService.getDocumentByIdAndCategoryId(categoryId, documentId));
    }
    
    @PostMapping("/categories/{categoryId}/documents")
    public DocumentResource createDocument(@PathVariable(name = "categoryId") Long categoryId,
                                     @Valid @RequestBody SaveDocumentResource resource){
        return convertToResource(documentService.createDocument(categoryId, convertToEntity(resource)));
    }


    @PutMapping("/categories/{categoryId}/documents/{documentId}")
    public DocumentResource updateDocument(@PathVariable(name = "categoryId") Long categoryId,
                                        @PathVariable(name = "documentId") Long documentId,
                                        @Valid @RequestBody SaveDocumentResource resource){
        return convertToResource(documentService.updateDocument(categoryId, documentId, convertToEntity(resource)));
    }


    @DeleteMapping("/categories/{categoryId}/documents/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable(name = "categoryId") Long categoryId,
                                            @PathVariable(name="documentId") Long documentId){
        return  documentService.deleteDocument(categoryId, documentId);
    }


    private Document convertToEntity(SaveDocumentResource resource) {
        return mapper.map(resource, Document.class);
    }

    private DocumentResource convertToResource(Document entity) {
        return mapper.map(entity, DocumentResource.class);
    }
}
