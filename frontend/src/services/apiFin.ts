import axios from 'axios';

const finApi = axios.create({
  baseURL: 'http://localhost:8084' 
});

finApi.interceptors.request.use(
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

export default finApi;