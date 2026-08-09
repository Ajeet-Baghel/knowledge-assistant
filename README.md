# AI Knowledge Assistant[RAG based]

An AI-powered document assistant built with **Spring Boot, Spring AI, PostgreSQL/pgvector, and Google Gemini**.

Users can upload PDF documents and ask questions about their content. The application uses **Retrieval-Augmented Generation (RAG)** to retrieve relevant document chunks before generating an answer.

## 🚀 Live Demo

- Frontend: https://knowledge-assistant-nine.vercel.app
- Backend: https://knowledge-assistant-s20w.onrender.com

## ✨ Features

- Upload PDF documents
- Extract and chunk document text
- Generate vector embeddings
- Store embeddings using PostgreSQL + pgvector
- Semantic similarity search
- RAG-based question answering
- Google Gemini for embeddings and response generation
- React frontend with Vite
- Deployed frontend and backend

## 🛠️ Tech Stack

### Frontend
- React
- Vite
- Axios

### Backend
- Java 21
- Spring Boot
- Spring AI
- Spring Data JPA
- Hibernate
- Apache PDFBox

### AI
- Google Gemini
- Gemini Embeddings
- Retrieval-Augmented Generation (RAG)

### Database
- PostgreSQL
- pgvector
- Neon

### Deployment
- Vercel — Frontend
- Render — Backend
- Neon — PostgreSQL

## 🏗️ Architecture

```text
┌─────────────────────┐
│      React + Vite   │
│      (Vercel)       │
└──────────┬──────────┘
           │ HTTPS
           ▼
┌─────────────────────┐
│    Spring Boot      │
│     (Render)        │
└──────┬─────────┬────┘
       │         │
       │         │
       ▼         ▼
┌────────────┐  ┌────────────────┐
│ PostgreSQL │  │ Google Gemini  │
│ + pgvector │  │ Embeddings +   │
│   (Neon)   │  │ Chat Model     │
└────────────┘  └────────────────┘


🔄 Document Processing Flow

PDF Upload
    ↓
PDF Text Extraction
    ↓
Text Chunking
    ↓
Generate Embeddings
    ↓
Store Chunks + Vectors
    ↓
PostgreSQL + pgvector


🧠 RAG Data Flow

User Question
      ↓
Generate Query Embedding
      ↓
Vector Similarity Search
      ↓
Retrieve Relevant Chunks
      ↓
Build RAG Prompt
      ↓
Google Gemini
      ↓
AI Generated Answer
