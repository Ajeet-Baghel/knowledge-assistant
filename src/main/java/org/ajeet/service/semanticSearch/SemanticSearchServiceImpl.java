package org.ajeet.service.semanticSearch;

import org.ajeet.dto.SemanticSearchResponse;
import org.ajeet.entity.DocumentChunk;
import org.ajeet.repository.DocumentChunkRepository;
import org.ajeet.service.embedding.EmbeddingService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

@Service
public class SemanticSearchServiceImpl implements SemanticSearchService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository documentChunkRepository;

    public SemanticSearchServiceImpl(
            EmbeddingService embeddingService,
            DocumentChunkRepository documentChunkRepository) {

        this.embeddingService = embeddingService;
        this.documentChunkRepository = documentChunkRepository;
    }

    @Override
    public List<SemanticSearchResponse> search(String query) {

        float[] embedding = embeddingService.generateEmbedding(query);

        String vector = toVector(embedding);

        List<DocumentChunk> chunks =
                documentChunkRepository.findSimilarChunks(vector, 5);

        List<SemanticSearchResponse> responses = new ArrayList<>();

        for (DocumentChunk chunk : chunks) {

            SemanticSearchResponse response =
                    new SemanticSearchResponse();

            response.setChunkNumber(chunk.getChunkNumber());
            response.setChunkText(chunk.getChunkText());

            responses.add(response);
        }

        return responses;
    }

    private String toVector(float[] embedding) {

        StringBuilder vector = new StringBuilder("[");

        for (int i = 0; i < embedding.length; i++) {

            vector.append(embedding[i]);

            if (i < embedding.length - 1) {
                vector.append(",");
            }
        }

        vector.append("]");

        return vector.toString();
    }

}