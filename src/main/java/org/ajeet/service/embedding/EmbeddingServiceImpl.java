package org.ajeet.service.embedding;

import org.springframework.ai.embedding.EmbeddingModel;
import org.ajeet.service.embedding.EmbedingService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingServiceImpl implements EmbedingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingServiceImpl(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public float[] generateEmbedding(String text) {

       return embeddingModel.embed(text);

    }

    @Override
    public List<float[]> generateEmbeddings(List<String> texts) {
        List<float[]> embeddings = new ArrayList<>();

        for (String text : texts) {
            embeddings.add(generateEmbedding(text));
        }

        return embeddings;

    }

}