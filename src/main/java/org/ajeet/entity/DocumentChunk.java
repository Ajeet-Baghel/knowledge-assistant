package org.ajeet.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_chunks")
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "chunk_number", nullable = false)
    private Integer chunkNumber;

    @Column(name = "chunk_text", nullable = false, columnDefinition = "TEXT")
    private String chunkText;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(name = "embedding")
    private float[] embedding;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public DocumentChunk() {

    }

    public DocumentChunk(Integer id, Document document, Integer chunkNumber, String chunkText, float[] embedding, LocalDateTime createdAt) {

        this.chunkNumber = chunkNumber;
        this.chunkText = chunkText;
        this.embedding = embedding;
        this.createdAt = createdAt;
        this.document = document;
        this.id = id;

    }


    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getChunkText() {
        return chunkText;
    }

    public void setChunkText(String chunkText) {
        this.chunkText = chunkText;
    }

}
