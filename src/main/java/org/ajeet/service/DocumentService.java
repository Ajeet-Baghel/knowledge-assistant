package org.ajeet.service;

//import org.ajeet.dto.DocumentRequest;
import org.ajeet.dto.DocumentResponse;
//import org.ajeet.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {


    DocumentResponse uploadDocument(MultipartFile file);

    List<DocumentResponse> getAllDocuments();

    void deleteDocument(Integer id);
}
