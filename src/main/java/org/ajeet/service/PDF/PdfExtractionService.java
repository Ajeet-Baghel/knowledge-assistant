package org.ajeet.service.PDF;

import java.nio.file.Path;

public interface PdfExtractionService {

    String extractText(Path pdfPath);
}
