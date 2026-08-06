import axios from 'axios';

// Central axios instance used by every service module.
// Base URL is left empty so requests go through the Vite dev-server
// proxy (see vite.config.js), which forwards to the Spring Boot backend.
const api = axios.create({
  baseURL: '/',
  timeout: 30000,
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.message ||
      error.response?.data ||
      error.message ||
      'Unexpected network error';
    return Promise.reject(new Error(message));
  }
);

export default api;
