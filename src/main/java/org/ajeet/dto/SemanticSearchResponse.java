package org.ajeet.dto;

public class SemanticSearchResponse {

    private Integer chunkNumber;
    private String chunkText;
//    private Double similarityScore;

    public SemanticSearchResponse() {
    }

    public SemanticSearchResponse(Integer chunkNumber, String chunkText) {
        this.chunkNumber = chunkNumber;
        this.chunkText = chunkText;

    }

    public Integer getChunkNumber() {
        return this.chunkNumber;

    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public String getChunkText() {
        return this.chunkText;
    }
    public void setChunkText(String chunkText) {
        this.chunkText = chunkText;
    }


}
