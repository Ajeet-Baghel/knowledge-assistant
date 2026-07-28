package org.ajeet.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

//import jakarta.validation.constraints.*;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @NotEmpty(message = "File name can't be empty")
    private String originalFileName;

    private String storedFileName;

    //    @NotEmpty(message = "File type can't be empty")
    private String fileType;


    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime uploadedAt;

//    Constructors

    public Document(int id, String originalFileName, String fileType, LocalDateTime uploadedAt, String storedFileName) {

        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileType = fileType;
        this.id = id;
        this.uploadedAt = uploadedAt;

    }

    public Document() {

    }

//    Setters and getters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return this.originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getUploadedAt() {
        return this.uploadedAt;
    }


    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }
}
