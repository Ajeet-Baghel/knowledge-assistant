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
    private final RagPromptBuilder ragPromptBuilder;

    public RagServiceImpl(ChatClient.Builder chatClientBuilder,
                          SemanticSearchService semanticSearchService,
                          RagPromptBuilder ragPromptBuilder) {

        this.chatClient = chatClientBuilder.build();
        this.semanticSearchService = semanticSearchService;
        this.ragPromptBuilder = ragPromptBuilder;
    }

    @Override
    public String ask(String question) {

        List<SemanticSearchResponse> chunks =
                semanticSearchService.search(question);

        String prompt = ragPromptBuilder.build(question, chunks);

        System.out.println("===== RAG DEBUG =====");
        System.out.println("Question: " + question);
        System.out.println("Chunks found: " + chunks.size());
        System.out.println("Prompt length: " + prompt.length());
        System.out.println("=====================");

        try {

            return chatClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content();

        } catch (Exception e) {

            System.err.println("===== GEMINI CHAT ERROR =====");
            e.printStackTrace();
            System.err.println("=============================");

            throw e;
        }
    }


//    @Override
//    public String ask(String question) {
//
//        List<SemanticSearchResponse> chunks = semanticSearchService.search(question);
//
//        String prompt = ragPromptBuilder.build(question, chunks);
//        return chatClient
//                .prompt()
//                .user(prompt)
//                .call()
//                .content();
//    }
}

