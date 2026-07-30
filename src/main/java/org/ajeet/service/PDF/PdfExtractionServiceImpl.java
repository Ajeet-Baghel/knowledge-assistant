package org.ajeet.service.PDF;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;


@Service
public class PdfExtractionServiceImpl implements PdfExtractionService {


    @Override
    public String extractText(Path pdfPath) {

        try (PDDocument document = Loader.loadPDF(pdfPath.toFile())) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String extractedText = pdfTextStripper.getText(document);
            return extractedText;

        } catch (IOException e) {
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }
}
