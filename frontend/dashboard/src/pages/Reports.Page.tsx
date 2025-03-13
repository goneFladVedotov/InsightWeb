// src/pages/ReportsPage.tsx
import React, { useEffect, useState } from 'react';
import { getPagesReport, getSourcesReport } from '../api/api';
import ReportTable from '../components/ReportTable';

function ReportsPage() {
  const [activeTab, setActiveTab] = useState<'pages' | 'sources'>('pages');
  const [pagesData, setPagesData] = useState([]);
  const [sourcesData, setSourcesData] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (activeTab === 'pages') fetchPagesReport();
    if (activeTab === 'sources') fetchSourcesReport();
  }, [activeTab]);

  async function fetchPagesReport() {
    try {
      setLoading(true);
      const data = await getPagesReport();
      setPagesData(data);
    } catch (err) {
      console.error('Error loading pages report:', err);
    } finally {
      setLoading(false);
    }
  }

  async function fetchSourcesReport() {
    try {
      setLoading(true);
      const data = await getSourcesReport();
      setSourcesData(data);
    } catch (err) {
      console.error('Error loading sources report:', err);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <h2>Отчёты</h2>
      <div className="tabs">
        <button
          onClick={() => setActiveTab('pages')}
          className={activeTab === 'pages' ? 'active' : ''}
        >
          По страницам
        </button>
        <button
          onClick={() => setActiveTab('sources')}
          className={activeTab === 'sources' ? 'active' : ''}
        >
          По источникам
        </button>
      </div>

      {loading && <div>Загрузка...</div>}

      {!loading && activeTab === 'pages' && (
        <ReportTable data={pagesData} columns={['url', 'pageViews', 'uniqueUsers', 'avgTime']} />
      )}

      {!loading && activeTab === 'sources' && (
        <ReportTable data={sourcesData} columns={['source', 'visits', 'conversions']} />
      )}
    </div>
  );
}

export default ReportsPage;
