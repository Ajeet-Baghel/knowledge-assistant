import api from './api';

// Maps to org.ajeet.controller.RagController (/rag/ask) and
// org.ajeet.controller.SemanticSearchController (/search).
const ragService = {
  ask: async (question) => {
    const { data } = await api.post('/rag/ask', { question });
    return data;
  },

  search: async (query) => {
    const { data } = await api.post('/search', { query });
    return data;
  },
};

export default ragService;
