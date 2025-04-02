package com.app.homeworkoutapplication.module.document.controller;

import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.module.document.service.DocumentService;
import com.app.homeworkoutapplication.module.document.service.criteria.DocumentCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class DocumentController {

    private final DocumentService documentService;

    private final QueryDocumentService queryDocumentService;

    public DocumentController(DocumentService documentService, QueryDocumentService queryDocumentService) {
        this.documentService = documentService;
        this.queryDocumentService = queryDocumentService;
    }

    @PostMapping("/documents")
    public ResponseEntity<Document> create(@Valid @RequestBody Document document) throws URISyntaxException {
        Document result = documentService.create(document);
        return ResponseEntity.created(new URI("/api/documents/" + result.getId())).body(result);
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<Document> update(@PathVariable("id") Long id, @Valid @RequestBody Document document){
        if (document.getId() == null) document.setId(id);
        Document res = documentService.update(document);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getDocumentPages(@ParameterObject DocumentCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Document> page = queryDocumentService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/documents/all")
    public ResponseEntity<List<Document>> getAllDocuments(@ParameterObject DocumentCriteria criteria){
        List<Document> documents = queryDocumentService.findListByCriteria(criteria);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> getById(@PathVariable("id") Long id){
        Document res = queryDocumentService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
