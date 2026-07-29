package org.ajeet.service.PDF;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class PdfExtractionServiceImpl implements PdfExtractionService {

    @Override
    public String extractText(Path pdfPath) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
