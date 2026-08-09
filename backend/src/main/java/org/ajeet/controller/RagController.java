package org.ajeet.controller;

import org.ajeet.dto.RagAskRequest;
import org.ajeet.service.rag.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody RagAskRequest request) {

        return ragService.ask(request.getQuestion());
    }

}
