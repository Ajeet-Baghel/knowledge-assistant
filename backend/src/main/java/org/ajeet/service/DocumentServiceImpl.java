package org.ajeet.service;

import org.ajeet.config.FileStorageConfig;
import org.ajeet.dto.DocumentResponse;
import org.ajeet.entity.Document;
import org.ajeet.entity.DocumentChunk;
import org.ajeet.repository.DocumentChunkRepository;
import org.ajeet.repository.DocumentRepository;
import org.ajeet.service.PDF.PdfExtractionService;
import org.ajeet.service.chunk.ChunkingService;
import org.ajeet.service.embedding.EmbeddingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ChunkingService textChunkingService;
    private final EmbeddingService embedingService;
    private final DocumentChunkRepository documentChunkRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               FileStorageConfig fileStorageConfig,
                               PdfExtractionService pdfExtractionService,
                               ChunkingService textChunkingService,
                               EmbeddingService embedingService,
                               DocumentChunkRepository documentChunkRepository) {

        this.documentRepository = documentRepository;
        this.fileStorageConfig = fileStorageConfig;
        this.pdfExtractionService = pdfExtractionService;
        this.textChunkingService = textChunkingService;
        this.embedingService = embedingService;
        this.documentChunkRepository = documentChunkRepository;
    }

    @Override
    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty.");
        }

        String uniqueFileName;
        Path targetPath;

        try {

            Path uploadPath = Paths.get(fileStorageConfig.getUploadDir());

            Files.createDirectories(uploadPath);

            uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            targetPath = uploadPath.resolve(uniqueFileName);

            file.transferTo(targetPath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        // Extract text
        String extractedText = pdfExtractionService.extractText(targetPath);

        // Chunk text
        List<String> chunks = textChunkingService.chunkText(extractedText);

        // Generate embeddings
        List<float[]> embeddings = embedingService.generateEmbeddings(chunks);

        // Safety check
        if (chunks.size() != embeddings.size()) {
            throw new IllegalStateException("Chunk and embedding counts do not match.");
        }

        // Save document metadata
        Document document = new Document();
        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(uniqueFileName);
        document.setFileType(file.getContentType());

        Document savedDocument = documentRepository.save(document);

        // Save all chunks
        saveDocumentChunks(savedDocument, chunks, embeddings);

        // Build response
        DocumentResponse response = new DocumentResponse();
        response.setId(savedDocument.getId());
        response.setOriginalFileName(savedDocument.getOriginalFileName());
        response.setStoredFileName(savedDocument.getStoredFileName());
        response.setFileType(savedDocument.getFileType());
        response.setUploadedAt(savedDocument.getUploadedAt());

        return response;
    }

    /**
     * Saves all chunks and their embeddings for a document.
     */
    private void saveDocumentChunks(
            Document document,
            List<String> chunks,
            List<float[]> embeddings) {

        List<DocumentChunk> documentChunks = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {

            DocumentChunk documentChunk = new DocumentChunk();

            documentChunk.setDocument(document);
            documentChunk.setChunkNumber(i + 1);
            documentChunk.setChunkText(chunks.get(i));
            documentChunk.setEmbedding(embeddings.get(i));

            documentChunks.add(documentChunk);
        }

        documentChunkRepository.saveAll(documentChunks);
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

    @Override
    public void deleteAllDocuments() {
        if(documentRepository != null)
        documentRepository.deleteAll();
    }
}