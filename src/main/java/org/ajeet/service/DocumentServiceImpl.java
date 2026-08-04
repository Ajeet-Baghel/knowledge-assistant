package org.ajeet.service;

import org.ajeet.config.FileStorageConfig;
import org.ajeet.dto.DocumentResponse;
import org.ajeet.entity.Document;
import org.ajeet.entity.DocumentChunk;
import org.ajeet.repository.DocumentChunkRepository;
import org.ajeet.repository.DocumentRepository;
import org.ajeet.service.PDF.PdfExtractionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ajeet.service.chunk.ChunkingService;
import org.ajeet.service.embedding.EmbedingService;

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
    private final EmbedingService embedingService;
    private final DocumentChunkRepository documentChunkRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               FileStorageConfig fileStorageConfig,
                               PdfExtractionService pdfExtractionService,
                               ChunkingService textChunkingService,
                               EmbedingService embedingService,
                               DocumentChunkRepository documentChunkRepository) {
        this.documentRepository = documentRepository;
        this.fileStorageConfig = fileStorageConfig;
        this.pdfExtractionService = pdfExtractionService;
        this.textChunkingService = textChunkingService;
        this.embedingService = embedingService;
        this.documentChunkRepository = documentChunkRepository;

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

        List<String> chunks = textChunkingService.chunkText(extractedText);

//        for (int i = 0; i < chunks.size(); i++) {      for local testing
//            System.out.println("===== CHUNK " + (i + 1) + " =====");
//            System.out.println(chunks.get(i));
//        }
        List<float[]> embeddings = embedingService.generateEmbeddings(chunks);

//        System.out.println("Chunks: " + chunks.size());  for testing embedding
//        System.out.println("Embeddings: " + embeddings.size());
//        System.out.println("Embedding Dimension: " + embeddings.get(0).length);

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

        List<DocumentChunk> documentChunks = new ArrayList<>();
        if (chunks.size() != embeddings.size()) {
            throw new IllegalStateException("Chunk and embedding counts do not match.");
        }

        for (int i = 0; i < chunks.size(); i++) {

            DocumentChunk documentChunk = new DocumentChunk();

            documentChunk.setDocument(savedDocument);
            documentChunk.setChunkNumber(i + 1);
            documentChunk.setChunkText(chunks.get(i));
            documentChunk.setEmbedding(embeddings.get(i));

            documentChunks.add(documentChunk);
        }
        documentChunkRepository.saveAll(documentChunks);
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