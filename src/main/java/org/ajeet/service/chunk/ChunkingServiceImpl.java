package org.ajeet.service.chunk;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingServiceImpl implements ChunkingService {

    @Override
    public List<String> chunkText(String text) {

        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        String[] paragraphs = text.split("\\R\\s*\\R");

        List<String> chunks = new ArrayList<>();

        final int MAX_CHUNK_SIZE = 500;

        StringBuilder currentChunk = new StringBuilder();

        for (String paragraph : paragraphs) {

            paragraph = paragraph.trim();

            if (!paragraph.isBlank()) {

                if (currentChunk.length() + paragraph.length() +2 > MAX_CHUNK_SIZE) {

                    chunks.add(currentChunk.toString().trim());

                    currentChunk.setLength(0);

                    currentChunk.append(paragraph).append("\n\n");

                } else {

                    currentChunk.append(paragraph).append("\n\n");
                }
            }
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }

}
