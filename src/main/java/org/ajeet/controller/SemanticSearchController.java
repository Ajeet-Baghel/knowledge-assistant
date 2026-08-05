package org.ajeet.controller;

import org.ajeet.dto.SemanticSearchRequest;
import org.ajeet.dto.SemanticSearchResponse;
import org.ajeet.service.semanticSearch.SemanticSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SemanticSearchController {

    private final SemanticSearchService semanticSearchService;

    public SemanticSearchController(SemanticSearchService semanticSearchService) {
        this.semanticSearchService = semanticSearchService;
    }

    @PostMapping()
    public List<SemanticSearchResponse> search(
            @RequestBody SemanticSearchRequest request) {

        return semanticSearchService.search(request.getQuery());

    }
}
