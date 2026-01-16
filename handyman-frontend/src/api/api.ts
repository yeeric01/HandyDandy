import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10000,
});

// Request interceptor (optional)
api.interceptors.request.use(
  (config) => {
    // attach auth token here later if needed
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor (optional)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API error:", error.response ?? error);
    return Promise.reject(error);
  }
);

export default api;
