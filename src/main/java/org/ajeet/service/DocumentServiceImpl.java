package org.ajeet.service;

import org.ajeet.config.FileStorageConfig;
import org.ajeet.dto.DocumentResponse;
import org.ajeet.entity.Document;
import org.ajeet.repository.DocumentRepository;
import org.ajeet.service.PDF.PdfExtractionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageConfig fileStorageConfig;
    private final PdfExtractionService pdfExtractionService;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               FileStorageConfig fileStorageConfig, PdfExtractionService pdfExtractionService) {
        this.documentRepository = documentRepository;
        this.fileStorageConfig = fileStorageConfig;
        this.pdfExtractionService = pdfExtractionService;
    }

    @Override
    public DocumentResponse uploadDocument(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty.");
        }

        String uniqueFileName;
        Path targetPath;

        try {

            // Upload directory
            Path uploadPath = Paths.get(fileStorageConfig.getUploadDir());

            // Create directory if it doesn't exist
            Files.createDirectories(uploadPath);

            // Generate unique filename
            uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Full path of the file
             targetPath = uploadPath.resolve(uniqueFileName);

            // Save file to disk
            file.transferTo(targetPath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        String extractedText = pdfExtractionService.extractText(targetPath);



        // Save metadata in database
        Document document = new Document();
        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(uniqueFileName);
        document.setFileType(file.getContentType());

        Document savedDocument = documentRepository.save(document);

        // Prepare response
        DocumentResponse response = new DocumentResponse();
        response.setId(savedDocument.getId());
        response.setOriginalFileName(savedDocument.getOriginalFileName());
        response.setStoredFileName(savedDocument.getStoredFileName());
        response.setFileType(savedDocument.getFileType());
        response.setUploadedAt(savedDocument.getUploadedAt());

        return response;
    }

    @Override
    public List<DocumentResponse> getAllDocuments() {

        List<Document> documents = documentRepository.findAll();
        List<DocumentResponse> responses = new ArrayList<>();

        for (Document document : documents) {

            DocumentResponse response = new DocumentResponse();

            response.setId(document.getId());
            response.setOriginalFileName(document.getOriginalFileName());
            response.setStoredFileName(document.getStoredFileName());
            response.setFileType(document.getFileType());
            response.setUploadedAt(document.getUploadedAt());

            responses.add(response);
        }

        return responses;
    }

    @Override
    public void deleteDocument(Integer id) {

        if (!documentRepository.existsById(id)) {
            throw new RuntimeException("Document not found with id: " + id);
        }

        documentRepository.deleteById(id);
    }
}