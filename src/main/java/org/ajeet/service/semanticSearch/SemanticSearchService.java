package org.ajeet.service.semanticSearch;

import org.ajeet.dto.SemanticSearchResponse;
import org.ajeet.entity.DocumentChunk;

import java.util.List;

public interface SemanticSearchService {

    List<SemanticSearchResponse> search(String query);
}
