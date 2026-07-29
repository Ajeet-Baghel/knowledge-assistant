# Requirements Document (RD)

## 1. Introduction

The **Enterprise Knowledge Assistant** is a Spring Boot backend that provides a Retrieval-Augmented Generation (RAG) service over enterprise documents. This document captures the functional and non-functional requirements for the system.

## 2. Scope

### 2.1 In Scope

- PDF document upload and metadata storage.
- Text extraction from PDF documents.
- Semantic text chunking.
- Embedding generation and storage in PostgreSQL using pgvector.
- Vector similarity search over stored embeddings.
- AI-generated answers grounded in retrieved document chunks.
- REST APIs for document management and chat.

### 2.2 Out of Scope

- Docker containerization
- Comprehensive unit and integration test suite
- Cloud deployment and CI/CD pipelines
- Web UI frontend
- User authentication and authorization

## 3. Functional Requirements

| ID | Requirement | Priority | Acceptance Criteria |
| --- | --- | --- | --- |
| FR-001 | Users can upload PDF documents via a REST API. | High | `POST /documents/upload` accepts multipart files up to 50 MB and returns metadata. |
| FR-002 | The system persists document metadata (original name, stored name, file type, upload time). | High | Metadata is saved to PostgreSQL and returned in list responses. |
| FR-003 | The system extracts text from uploaded PDF files. | High | Text content is extracted and available for chunking. |
| FR-004 | The system splits extracted text into semantic chunks. | High | Documents are broken into overlapping chunks suitable for embedding. |
| FR-005 | The system generates vector embeddings for each chunk. | High | Each chunk is converted to an embedding vector. |
| FR-006 | The system stores embeddings in PostgreSQL using pgvector. | High | Embeddings are persisted in a vector-enabled table. |
| FR-007 | The system retrieves relevant chunks using vector similarity search. | High | Top-K relevant chunks are returned for a given query embedding. |
| FR-008 | The system generates AI responses based on retrieved chunks and the user question. | High | Chat endpoint returns a context-aware answer. |
| FR-009 | Users can include multiple uploaded documents in a single chat session. | Medium | Chat context can reference more than one document. |
| FR-010 | The system supports DOCX and TXT documents. | Medium | DOCX and TXT files can be uploaded and processed. |
| FR-011 | Users can filter similarity search results by metadata. | Medium | Queries can include metadata filters. |
| FR-012 | The system can stream AI responses to the client. | Medium | Chat endpoint supports streaming output. |

## 4. Non-Functional Requirements

| ID | Category | Requirement |
| --- | --- | --- |
| NFR-001 | Performance | The upload endpoint handles files up to 50 MB. |
| NFR-002 | Reliability | REST APIs return JSON responses with consistent status codes. |
| NFR-003 | Maintainability | The code follows a layered architecture (controller → service → repository). |
| NFR-004 | Compatibility | The application runs on Java 21 and Spring Boot 4.1. |
| NFR-005 | Data | Database schema is managed automatically in development. |
| NFR-006 | Usability | API errors return meaningful HTTP status codes and messages. |

## 5. Constraints & Assumptions

### 5.1 Constraints

- Java 21+, Spring Boot 4.1, Maven.
- PostgreSQL with the pgvector extension.
- Spring AI 2.0 for model and vector-store integration.
- LLM access via Ollama, OpenAI, or Gemini compatible API.

### 5.2 Assumptions

- The PostgreSQL instance and pgvector extension are pre-installed.
- The LLM service is available locally or via API key.
- Documents do not contain sensitive data requiring encryption at rest.

## 6. Dependencies

- PostgreSQL server
- pgvector extension enabled
- Ollama or API access to an OpenAI / Gemini compatible LLM
- Maven 3.9+
