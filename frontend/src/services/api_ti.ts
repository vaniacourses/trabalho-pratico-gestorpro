import axios from 'axios';

const api = axios.create({
    // A URL base deve ser a do seu API Gateway (porta 8080)
    baseURL: 'http://localhost:8080', 
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken'); // Use o nome que seu AuthContext salva
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;