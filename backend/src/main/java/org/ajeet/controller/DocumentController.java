package org.ajeet.controller;

import org.ajeet.dto.DocumentResponse;
import org.ajeet.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * The DocumentController class is a REST controller that handles document-related operations.
 * It provides endpoints for uploading, deleting, and listing documents.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
//    TODO: Add chunk size and overlap as properties
//    private static final int MAX_CHUNK_SIZE = 800;
//    private static final int CHUNK_OVERLAP = 150;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Get all documents
     * @return List of documents
     */
    @GetMapping()
    public List<DocumentResponse> getAllDocument() {
        return this.documentService.getAllDocuments();
    }

    /**
     * Upload a document
     * @param file The document to upload
     * @return The uploaded document
     */
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestParam("file") MultipartFile file) {
        DocumentResponse response = documentService.uploadDocument(file);
        return ResponseEntity.ok(response);

    }

    /**
     * Delete a document
     * @param id The id of the document to delete
     * @return A message indicating that the document was deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Integer id) {
        this.documentService.deleteDocument(id);
        return ResponseEntity.ok("Deleted ");
    }

    /**
     * Delete all documents
     * @return A message indicating that all documents were deleted
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllDocuments() {
        this.documentService.deleteAllDocuments();
        return ResponseEntity.ok("Deleted All");
    }
}
