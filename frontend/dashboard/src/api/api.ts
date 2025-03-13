// src/api/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:4000/api', // URL вашего бэкенда
  withCredentials: true,               // если нужно отправлять куки/сессии
});

// Примеры методов
export async function getDashboardData(params?: { from?: string; to?: string }) {
  // GET /dashboard?from=YYYY-MM-DD&to=YYYY-MM-DD
  const response = await api.get('/dashboard', { params });
  return response.data;
}

export async function getPagesReport(params?: { from?: string; to?: string }) {
  // GET /reports/pages
  const response = await api.get('/reports/pages', { params });
  return response.data;
}

export async function getSourcesReport(params?: { from?: string; to?: string }) {
  // GET /reports/sources
  const response = await api.get('/reports/sources', { params });
  return response.data;
}

// Пример сохранения настроек трекера
export async function updateTrackerSettings(settings: any) {
  // PATCH /settings/tracker
  const response = await api.patch('/settings/tracker', settings);
  return response.data;
}

export default api;  // общий axios-инстанс (можно использовать для других запросов)
