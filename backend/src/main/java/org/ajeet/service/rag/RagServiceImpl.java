package org.ajeet.service.rag;

import org.ajeet.dto.SemanticSearchResponse;
import org.ajeet.service.semanticSearch.SemanticSearchService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagServiceImpl implements RagService {

    private final ChatClient chatClient;
    private final SemanticSearchService semanticSearchService;

    public RagServiceImpl(ChatClient.Builder chatClientBuilder,
                          SemanticSearchService semanticSearchService) {

        this.chatClient = chatClientBuilder.build();
        this.semanticSearchService = semanticSearchService;
    }

    @Override
    public String ask(String question) {

        List<SemanticSearchResponse> chunks = semanticSearchService.search(question);

        StringBuilder context = new StringBuilder();

        for (SemanticSearchResponse chunk : chunks) {
            context.append(chunk.getChunkText())
                    .append("\n\n");
        }

        String prompt = """
                You are a helpful AI assistant.
                
                Answer the user's question using ONLY the provided context.
                
                If the answer is not present in the context,
                say "I couldn't find that information in the uploaded documents."
                
                Context:
                
                %s
                
                Question:
                
                %s
                """.formatted(context.toString(), question);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
