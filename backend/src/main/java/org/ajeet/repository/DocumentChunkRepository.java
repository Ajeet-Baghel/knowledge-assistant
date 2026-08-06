package org.ajeet.repository;

import org.ajeet.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DocumentChunkRepository
        extends JpaRepository<DocumentChunk, Integer> {

    @Query(value = """
            SELECT *
            FROM document_chunks
            ORDER BY embedding <=> CAST(:queryEmbedding AS vector)
            LIMIT :limit
            """, nativeQuery = true)
    List<DocumentChunk> findSimilarChunks(
            @Param("queryEmbedding") String queryEmbedding,
            @Param("limit") int limit
    );

}

