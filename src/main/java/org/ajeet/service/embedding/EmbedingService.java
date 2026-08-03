package org.ajeet.service.embedding;
import java.util.List;


public interface EmbedingService {

    float[] generateEmbedding(String text);

   List<float[]> generateEmbeddings(List<String> texts);
}
