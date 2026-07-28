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
    private String fileName;

//    @NotEmpty(message = "File type can't be empty")
    private String fileType;


    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime uploadedAt;

//    Constructors

    public Document(int id, String fileName, String fileType, LocalDateTime uploadedAt) {

        this.fileName = fileName;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    private void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
