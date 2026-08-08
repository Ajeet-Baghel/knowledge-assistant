package org.ajeet.service.rag;

import org.ajeet.dto.SemanticSearchResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RagPromptBuilder {

    public String build(String question, List<SemanticSearchResponse> chunks) {

        StringBuilder context = new StringBuilder();

        for (SemanticSearchResponse chunk : chunks) {
            context.append(chunk.getChunkText())
                    .append("\n\n");
        }

        return """
                You are a helpful AI assistant.

                Answer the user's question using ONLY the provided context.

                If the answer is not present in the context,
                say "I couldn't find that information in the uploaded documents."

                Context:

                %s

                Question:

                %s
                """.formatted(context.toString(), question);
    }
}