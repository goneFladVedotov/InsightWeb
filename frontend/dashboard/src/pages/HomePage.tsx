// src/pages/HomePage.tsx
import React, { useEffect, useState } from 'react';
import { getDashboardData } from '../api/api';
import TrafficChart from '../components/Charts/TrafficChart';

function HomePage() {
  const [loading, setLoading] = useState(false);
  const [metrics, setMetrics] = useState<{
    pageViews?: number;
    uniqueUsers?: number;
    conversions?: number;
    chartData?: any;
  }>({});

  useEffect(() => {
    fetchData();
  }, []);

  async function fetchData() {
    try {
      setLoading(true);
      const data = await getDashboardData();
      setMetrics(data);
    } catch (err) {
      console.error('Error loading dashboard data:', err);
    } finally {
      setLoading(false);
    }
  }

  if (loading) return <div>Загрузка...</div>;

  return (
    <div>
      <h2>Обзор</h2>
      <div className="metrics-cards">
        <div className="metric-card">
          <h3>Просмотры страниц</h3>
          <p>{metrics.pageViews || 0}</p>
        </div>
        <div className="metric-card">
          <h3>Уникальные пользователи</h3>
          <p>{metrics.uniqueUsers || 0}</p>
        </div>
        <div className="metric-card">
          <h3>Конверсии</h3>
          <p>{metrics.conversions || 0}</p>
        </div>
      </div>
      <div className="chart-section">
        <h3>Трафик по дням</h3>
        <TrafficChart chartData={metrics.chartData || {}} />
      </div>
    </div>
  );
}

export default HomePage;
