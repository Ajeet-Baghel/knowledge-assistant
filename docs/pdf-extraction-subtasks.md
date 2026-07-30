# Sub-tasks: Extract Text from Uploaded PDFs

---

## Task 1: Implement PDF text extraction service

```markdown
## Description
Implement a service that extracts text content from an uploaded PDF using Spring AI's `PagePdfDocumentReader` (backed by Apache PDFBox). This includes adding the required dependency and building the core extraction logic.

## Tasks
- [ ] Add the necessary Spring AI PDF reader dependency
- [ ] Create a `PdfExtractionService` that accepts a PDF file/resource and returns extracted text
- [ ] Extract content page by page, preserving page number metadata where available
- [ ] Return results as a list of `Document` objects (or plain text, per pipeline design)

## Acceptance Criteria
- Given a valid, text-based PDF, extraction returns the correct textual content
- Multi-page PDFs return content from all pages
```

---

## Task 2: Integrate extraction into the document upload flow

```markdown
## Description
Wire up the PDF extraction service into the existing `/documents/upload` endpoint so extraction runs automatically after a PDF is uploaded, and the extracted content is passed to the next pipeline stage (chunking).

## Tasks
- [ ] Call the extraction service after file upload/storage in the upload flow
- [ ] Pass extracted text/documents forward to the chunking stage
- [ ] Add logging for extraction start/success/failure

## Acceptance Criteria
- Uploading a PDF triggers extraction automatically
- Extracted content is available for the next pipeline stage
```

---

## Task 3: Handle invalid, corrupted, or non-extractable PDFs

```markdown
## Description
Add error handling for PDFs that are invalid/corrupted, and for PDFs with no extractable text (e.g., scanned/image-only documents), so the system fails gracefully in both cases instead of crashing or silently returning nothing.

## Tasks
- [ ] Catch and wrap exceptions thrown during parsing (e.g., `PdfExtractionException`)
- [ ] Return a clear error/status when the upload endpoint receives an invalid or corrupted file
- [ ] Detect when extraction yields empty/near-empty text and log/flag it as "no extractable text found"
- [ ] Document that OCR for scanned PDFs is out of scope for this story

## Acceptance Criteria
- Uploading a non-PDF or corrupted file does not crash the application and returns a clear error
- Scanned/image-only PDFs with no text layer do not cause errors and are clearly flagged
```

---

## Task 4: Manual verification with sample PDFs

```markdown
## Description
Manually verify the end-to-end extraction flow using a set of representative sample PDFs to confirm correctness and error handling.

## Tasks
- [ ] Test with a simple single-page text PDF
- [ ] Test with a multi-page PDF
- [ ] Test with a corrupted/invalid file
- [ ] Test with a scanned/image-only PDF
- [ ] Document results/observations

## Acceptance Criteria
- All test cases produce expected behavior as defined in Tasks 1 and 3
```
