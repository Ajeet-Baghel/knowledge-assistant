# Enterprise Knowledge Assistant (RAG Backend)

A **Spring Boot** based Retrieval-Augmented Generation (RAG) backend application that allows users to upload enterprise documents, generate vector embeddings, and query them using an AI-powered chat interface.

The project demonstrates how modern enterprise applications integrate **Large Language Models (LLMs)** with **vector databases** to provide accurate, context-aware responses from private documents.

---

## Features

### Phase 1 (Current)

* Upload PDF documents
* Store document metadata
* Extract text from PDF files
* Split documents into semantic chunks
* Generate vector embeddings
* Store embeddings in PostgreSQL using **pgvector**
* Retrieve relevant chunks using vector similarity search
* Generate AI responses based on retrieved document context

### Planned Features

* Multiple document support
* DOCX and TXT document support
* Metadata filtering
* Streaming AI responses
* Docker support
* Unit and Integration Tests
* Cloud deployment

---

## Tech Stack

| Category        | Technology               |
| --------------- | ------------------------ |
| Language        | Java 21                  |
| Framework       | Spring Boot 3            |
| Build Tool      | Maven                    |
| Database        | PostgreSQL               |
| Vector Database | pgvector                 |
| ORM             | Spring Data JPA          |
| AI Integration  | Spring AI                |
| LLM             | Ollama / OpenAI / Gemini |
| PDF Processing  | Apache PDFBox            |
| API Testing     | Postman                  |

---

## Project Architecture

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

---

## Project Structure

```text
src/main/java/com/ajeet/rag

├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
├── util
└── EnterpriseKnowledgeAssistantApplication.java
```

---

## Database Schema

### documents

| Column      | Type         |
| ----------- | ------------ |
| id          | SERIAL       |
| file_name   | VARCHAR(255) |
| file_type   | VARCHAR(50)  |
| uploaded_at | TIMESTAMP    |

---

### document_chunks

| Column       | Type   |
| ------------ | ------ |
| id           | SERIAL |
| document_id  | INT    |
| chunk_number | INT    |
| chunk_text   | TEXT   |
| embedding    | VECTOR |

---

## REST APIs

### Document APIs

| Method | Endpoint                | Description                |
| ------ | ----------------------- | -------------------------- |
| POST   | `/api/documents/upload` | Upload a document          |
| GET    | `/api/documents`        | Get all uploaded documents |
| DELETE | `/api/documents/{id}`   | Delete a document          |

---

### Chat API

| Method | Endpoint    | Description                           |
| ------ | ----------- | ------------------------------------- |
| POST   | `/api/chat` | Ask questions from uploaded documents |

---

## RAG Workflow

1. Upload a PDF document.
2. Extract text from the document.
3. Split the text into meaningful chunks.
4. Generate embeddings for each chunk.
5. Store embeddings in PostgreSQL using pgvector.
6. Convert the user's question into an embedding.
7. Retrieve the most relevant chunks using vector similarity search.
8. Send the retrieved context and question to the LLM.
9. Return an AI-generated answer.

---

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd enterprise-rag-backend
```

### Configure PostgreSQL

Create a PostgreSQL database and enable the pgvector extension.

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

Update your `application.yml` or `application.properties` with your database credentials.

### Run the Application

```bash
mvn spring-boot:run
```

---

## Future Roadmap

* [ ] PDF Upload API
* [ ] PDF Text Extraction
* [ ] Text Chunking
* [ ] Embedding Generation
* [ ] Vector Storage
* [ ] Semantic Search
* [ ] AI Chat Integration
* [ ] Docker Support
* [ ] Deployment
* [ ] Testing

---

## Learning Objectives

This project demonstrates practical implementation of:

* Spring Boot REST APIs
* Layered Architecture
* Spring Data JPA
* PostgreSQL
* pgvector
* Vector Embeddings
* Semantic Search
* Retrieval-Augmented Generation (RAG)
* Enterprise Backend Development
* AI Integration using Spring AI

---

## License

This project is created for educational and portfolio purposes.
