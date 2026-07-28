package org.ajeet.dto;

public class DocumentRequest {

    private String fileName;
    private String fileType;

    public DocumentRequest() {
    }

    public DocumentRequest(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
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
}