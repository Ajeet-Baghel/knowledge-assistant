import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Vite dev server proxies /documents, /rag, /search to the Spring Boot
// backend (port 8080) so the browser can call relative paths without
// hitting CORS issues, since the backend has no CORS config yet.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/documents': 'http://localhost:8080',
      '/rag': 'http://localhost:8080',
      '/search': 'http://localhost:8080',
    },
  },
});
