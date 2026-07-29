# Project Plan / Roadmap

## 1. Overview

This roadmap defines the delivery phases and milestones for the Enterprise Knowledge Assistant. It tracks tasks, ownership, current status, dependencies, and key notes.

## 2. Milestones

| Milestone | Target | Goal |
| --- | --- | --- |
| MVP | Phase 1 complete | End-to-end RAG flow for PDF documents. |
| Extended Support | Phase 2 complete | Multi-document, DOCX/TXT, metadata filtering, streaming. |
| Hardening | Phase 3 complete | Validation, logging, exception handling, rate limiting. |

## 3. Phase 1 — Core RAG Pipeline

**Goal:** Enable a complete document-to-answer flow for PDF documents.  
**Estimated Duration:** 2–3 weeks

| Task | Owner | Status | Dependencies | Notes |
| --- | --- | --- | --- | --- |
| Upload PDF documents via REST API | TBD | Complete | — | `POST /documents/upload` implemented. |
| Store document metadata in PostgreSQL | TBD | Complete | FR-002 | Saved to PostgreSQL via JPA. |
| Extract text from uploaded PDFs | TBD | Not Complete | FR-003 | `PdfExtractionServiceImpl` is a stub. |
| Split text into semantic chunks | TBD | Not Complete | FR-004 | No chunking service/entity yet. |
| Generate vector embeddings for chunks | TBD | Not Complete | FR-005 | Embedding generation not wired. |
| Store embeddings in pgvector | TBD | Not Complete | FR-006 | `vector_store` not in use yet. |
| Vector similarity search | TBD | Not Complete | FR-007 | Vector search not implemented. |
| Generate AI responses from retrieved context | TBD | Not Complete | FR-008 | Chat endpoint not implemented. |

## 4. Phase 2 — Extended Document & Query Support

**Goal:** Support more document types and richer queries.  
**Estimated Duration:** 1–2 weeks

| Task | Owner | Status | Dependencies | Notes |
| --- | --- | --- | --- | --- |
| Multiple document support per chat | TBD | Not Complete | FR-009 | — |
| DOCX document support | TBD | Not Complete | FR-010 | — |
| TXT document support | TBD | Not Complete | FR-010 | — |
| Metadata filtering in search | TBD | Not Complete | FR-011 | — |
| Streaming AI responses | TBD | Not Complete | FR-012 | — |

## 5. Phase 3 — Robustness & Quality

**Goal:** Improve reliability, observability, and user experience.  
**Estimated Duration:** 1 week

| Task | Owner | Status | Dependencies | Notes |
| --- | --- | --- | --- | --- |
| Global exception handling | TBD | Not Complete | — | — |
| Input validation and file-type checks | TBD | Not Complete | — | — |
| Logging and request tracing | TBD | Not Complete | — | — |
| API rate limiting | TBD | Not Complete | — | — |

## 6. Out of Scope

The following are intentionally excluded from this roadmap:

- Docker support
- Unit and Integration Tests
- Cloud deployment

## 7. Risks & Mitigations

| Risk | Mitigation |
| --- | --- |
| LLM setup delays | Start with local Ollama; add OpenAI/Gemini fallback later. |
| Chunking quality | Prototype with fixed-size overlap and iterate. |
| Vector DB setup | Validate pgvector extension early. |

## 8. Next Steps

1. Implement PDF text extraction using the Spring AI PDF reader.
2. Add chunking and embedding generation.
3. Wire `PgVectorStore` and verify similarity search.
4. Add the `/api/chat` endpoint and `ChatClient`.
