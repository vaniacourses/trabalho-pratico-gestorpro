import axios from 'axios';

const adminApi = axios.create({
  baseURL: 'http://localhost:8080' 
});

adminApi.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('authToken');

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default adminApi;