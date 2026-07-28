package org.ajeet.controller;

import jakarta.validation.Valid;
import org.ajeet.dto.DocumentRequest;
import org.ajeet.dto.DocumentResponse;
import org.ajeet.entity.Document;
import org.ajeet.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

//    Request mappings

    @GetMapping()
    public List<DocumentResponse> getAllDocument() {
        return this.documentService.getAllDocuments();
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestParam("file") MultipartFile file) {
        DocumentResponse response = documentService.uploadDocument(file);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Integer id) {
        this.documentService.deleteDocument(id);
        return ResponseEntity.ok("Deleted ");
    }
}
