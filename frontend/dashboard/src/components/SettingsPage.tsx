// src/pages/SettingsPage.tsx
import React, { useEffect, useState } from 'react';
import { updateTrackerSettings } from '../api/api';

function SettingsPage() {
  const [trackPageViews, setTrackPageViews] = useState(true);
  const [trackClicks, setTrackClicks] = useState(true);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // В реальном случае мы бы делали getSettings() и загружали текущие настройки
  }, []);

  async function handleSave() {
    try {
      setLoading(true);
      const newSettings = { trackPageViews, trackClicks };
      await updateTrackerSettings(newSettings);
      alert('Настройки сохранены!');
    } catch (err) {
      console.error('Error saving settings', err);
      alert('Ошибка при сохранении настроек');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <h2>Настройки трекера</h2>
      <div>
        <label>
          <input
            type="checkbox"
            checked={trackPageViews}
            onChange={(e) => setTrackPageViews(e.target.checked)}
          />
          Отслеживать просмотры страниц
        </label>
      </div>
      <div>
        <label>
          <input
            type="checkbox"
            checked={trackClicks}
            onChange={(e) => setTrackClicks(e.target.checked)}
          />
          Отслеживать клики
        </label>
      </div>

      <button onClick={handleSave} disabled={loading}>
        {loading ? 'Сохранение...' : 'Сохранить'}
      </button>
    </div>
  );
}

export default SettingsPage;
