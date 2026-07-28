package org.ajeet.service;

import org.ajeet.dto.DocumentRequest;
import org.ajeet.dto.DocumentResponse;
import org.ajeet.entity.Document;
import org.ajeet.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentResponse uploadDocument(MultipartFile file) {

        Document document = new Document();

        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());

        // save document into repository

        Document savedDocument = documentRepository.save(document);

        DocumentResponse documentResponse = new DocumentResponse();

        documentResponse.setId(savedDocument.getId());
        documentResponse.setFileName(savedDocument.getFileName());
        documentResponse.setFileType(savedDocument.getFileType());
        documentResponse.setUploadedAt(savedDocument.getUploadedAt());

        return documentResponse;
    }

    @Override
    public List<DocumentResponse> getAllDocuments() {

        List<Document> documents = documentRepository.findAll();

        List<DocumentResponse> documentResponses = new ArrayList<>();

        for(Document document: documents) {

            DocumentResponse response = new DocumentResponse();

            response.setId(document.getId());
            response.setFileName(document.getFileName());
            response.setFileType(document.getFileType());
            response.setUploadedAt(document.getUploadedAt());

            documentResponses.add(response);
        }


        return documentResponses;
    }

    @Override
    public void deleteDocument(Integer id) {
        if (this.documentRepository.existsById(id)) {
            this.documentRepository.deleteById(id);
        } else {
            System.out.print("Document not found");
        }

    }
}
