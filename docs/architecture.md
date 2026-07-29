# Architecture & Design

## 1. Overview

The Enterprise Knowledge Assistant uses a layered Spring Boot architecture. Documents are uploaded, processed into embeddings, and stored in PostgreSQL with the pgvector extension. User questions are converted into embeddings, matched against stored chunks, and sent to an LLM with retrieved context.

## 2. Architecture Goals

- **Modularity:** Each concern (upload, extraction, chunking, embedding, search) is isolated.
- **Extensibility:** New document types and LLMs can be added behind interfaces.
- **Simplicity:** Minimal moving parts for a portfolio/educational project.

## 3. High-Level Architecture

```text
                  Upload Document
                         │
                         ▼
               Document Controller
                         │
                         ▼
                Document Service
                         │
                         ▼
                  PDF Extraction
                         │
                         ▼
                  Text Chunking
                         │
                         ▼
              Embedding Generation
                         │
                         ▼
             PostgreSQL + pgvector
---------------------------------------------------
                         │
                    User Question
                         │
                         ▼
              Question Embedding
                         │
                         ▼
              Vector Similarity Search
                         │
                         ▼
               Relevant Document Chunks
                         │
                         ▼
                     Large Language Model
                         │
                         ▼
                    AI Generated Answer
```

## 4. Component Descriptions

| Component | Responsibility |
| --- | --- |
| Document Controller | Exposes REST endpoints for document upload, list, delete, and chat. |
| Document Service | Orchestrates file storage, metadata persistence, and pipeline coordination. |
| PDF Extraction Service | Extracts raw text from uploaded PDF files. |
| Chunking Service | Splits text into overlapping semantic chunks. |
| Embedding Service | Generates vector embeddings for chunks and questions. |
| Vector Store | Persists and searches embeddings using PostgreSQL + pgvector. |
| Chat Client | Sends retrieved context + user question to the LLM. |

## 5. Data Flow

### 5.1 Ingestion Flow

1. User uploads a PDF via `POST /documents/upload`.
2. `DocumentController` forwards the file to `DocumentService`.
3. `DocumentService` saves the file to disk and stores metadata in PostgreSQL.
4. The PDF is sent to `PdfExtractionService` to extract text.
5. Text is split into chunks by the Chunking Service.
6. The Embedding Service generates vectors for each chunk.
7. Vectors are stored in pgvector.

### 5.2 Query Flow

1. User sends a question to `POST /api/chat`.
2. The question is embedded by the Embedding Service.
3. The Vector Store performs similarity search.
4. Top-K chunks are passed to the Chat Client.
5. The LLM generates an answer using the chunks as context.

## 6. Project Structure

```text
src/main/java/org/ajeet/
├── Main.java
├── config/
│   └── FileStorageConfig.java
├── controller/
│   └── DocumentController.java
├── dto/
│   ├── DocumentRequest.java
│   └── DocumentResponse.java
├── entity/
│   └── Document.java
├── repository/
│   └── DocumentRepository.java
├── service/
│   ├── DocumentService.java
│   ├── DocumentServiceImpl.java
│   └── PDF/
│       ├── PdfExtractionService.java
│       └── PdfExtractionServiceImpl.java
└── resources/
    └── application.yaml
```

## 7. Database Schema

### 7.1 `documents`

| Column | Type | Description |
| --- | --- | --- |
| id | SERIAL | Primary key |
| original_file_name | VARCHAR(255) | Name of the uploaded file |
| stored_file_name | VARCHAR(255) | Unique stored file name |
| file_type | VARCHAR(50) | File extension/type |
| uploaded_at | TIMESTAMP | Upload timestamp |

### 7.2 `document_chunks` (planned)

| Column | Type | Description |
| --- | --- | --- |
| id | SERIAL | Primary key |
| document_id | INT | Foreign key to documents |
| chunk_number | INT | Position in document |
| chunk_text | TEXT | Raw chunk content |
| embedding | VECTOR | Embedding vector |

## 8. REST API Specification

### 8.1 Document APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/documents` | Get all uploaded documents |
| POST | `/documents/upload` | Upload a document |
| DELETE | `/documents/{id}` | Delete a document |

### 8.2 Chat API

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/chat` | Ask questions from uploaded documents |

## 9. Technology Mapping

| Layer | Technology |
| --- | --- |
| Web Layer | Spring Web (Spring Boot 4.1) |
| Data Layer | Spring Data JPA, PostgreSQL |
| Vector Store | pgvector extension, Spring AI PgVectorStore |
| AI Model | Spring AI ChatClient (Ollama / OpenAI / Gemini) |
| PDF Parsing | Apache PDFBox via Spring AI PDF reader |
| File Storage | Local filesystem |

## 10. Design Decisions

- **File storage on local disk** keeps the first version simple.
- **JPA entities** manage relational metadata; vector chunks use Spring AI vector-store abstraction.
- **Interface-based services** (`PdfExtractionService`) allow swapping implementations.
- **Multipart upload** is configured with 50 MB file / 100 MB request limits.

## 11. Future Considerations

- Add an API prefix such as `/api` across controllers.
- Introduce a dedicated `chunk` package once chunking/embedding grows.
- Consider a separate embedding cache if search latency becomes an issue.
