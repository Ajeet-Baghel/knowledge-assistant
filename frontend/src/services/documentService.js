import api from './api';

// Maps 1:1 to org.ajeet.controller.DocumentController on the backend.
const documentService = {
  getAll: async () => {
    const { data } = await api.get('/documents');
    return data;
  },

  upload: async (file, onUploadProgress) => {
    const formData = new FormData();
    formData.append('file', file);
    const { data } = await api.post('/documents/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      onUploadProgress,
    });
    return data;
  },

  remove: async (id) => {
    const { data } = await api.delete(`/documents/${id}`);
    return data;
  },

  removeAll: async () => {
    const { data } = await api.delete('/documents/deleteAll');
    return data;
  },
};

export default documentService;
