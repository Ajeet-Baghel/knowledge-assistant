package org.ajeet.dto;

public class SemanticSearchRequest {

    private String query;

    public SemanticSearchRequest() {
    }

    public SemanticSearchRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
