package org.ajeet.service.chunk;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingServiceImpl implements ChunkingService {

    private static final int MAX_CHUNK_SIZE = 800;
    private static final int CHUNK_OVERLAP = 150;

    @Override
    public List<String> chunkText(String text) {

        String[] paragraphs = text.split("\\n\\s*\\n");

        List<String> chunks = new ArrayList<>();

        StringBuilder currentChunk = new StringBuilder();

        for (String paragraph : paragraphs) {

            paragraph = paragraph.trim();

            if (paragraph.isBlank()) {
                continue;
            }

            if (currentChunk.length() + paragraph.length() > MAX_CHUNK_SIZE) {

                if (!currentChunk.isEmpty()) {
                    chunks.add(currentChunk.toString().trim());
                    currentChunk.setLength(0);
                }

                chunks.add(currentChunk.toString().trim());

                currentChunk.setLength(0);
            }

            currentChunk.append(paragraph)
                    .append("\n\n");
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }


}
