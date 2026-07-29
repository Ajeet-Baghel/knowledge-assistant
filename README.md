# Enterprise Knowledge Assistant (RAG Backend)

A **Spring Boot** based Retrieval-Augmented Generation (RAG) backend that allows users to upload enterprise documents, generate vector embeddings, and query them using an AI-powered chat interface.

Detailed documentation is maintained in the `docs/` directory:

- [Requirements Document](docs/requirements.md)
- [Product Requirements Document](docs/prd.md)
- [Project Roadmap](docs/project-roadmap.md)
- [Architecture & Design](docs/architecture.md)

## Tech Stack

| Category        | Technology               |
| --------------- | ------------------------ |
| Language        | Java 21                  |
| Framework       | Spring Boot 4.1          |
| Build Tool      | Maven                    |
| Database        | PostgreSQL               |
| Vector Database | pgvector                 |
| ORM             | Spring Data JPA          |
| AI Integration  | Spring AI 2.0            |
| LLM             | Ollama / OpenAI / Gemini |
| PDF Processing  | Apache PDFBox            |
| API Testing     | Postman                  |

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd knowledge-assistant
```

### Configure PostgreSQL

Create a PostgreSQL database and enable the pgvector extension.

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

Update `src/main/resources/application.yaml` with your database credentials.

### Run the Application

```bash
mvn spring-boot:run
```

## License

This project is created for educational and portfolio purposes.
