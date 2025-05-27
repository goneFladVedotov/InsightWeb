import React, { useState, useEffect } from 'react';

interface MetricEvent {
    type: keyof QuickMetricsData;
    data: any;
}

type QuickMetricsData = {
    activeSessions: number;
    clicks: {
        totalLast5Min: number;
        topElements: Array<{ element_id: string; count: number }>;
    };
    errors: {
        counts: Record<string, number>;
        spikes: Array<{ browser?: string; os?: string; count: number }>;
    };
    searchQueries: string[];
    conversions: { purchases: number; signups: number };
    revenue: number;
    averageScrollDepth: number;
    geography: Array<{ city: string; country: string; users: number }>;
    devices: { mobile: number; desktop: number };
    languages: string[];
};

const initialMetrics: QuickMetricsData = {
    activeSessions: 0,
    clicks: { totalLast5Min: 0, topElements: [] },
    errors: { counts: {}, spikes: [] },
    searchQueries: [],
    conversions: { purchases: 0, signups: 0 },
    revenue: 0,
    averageScrollDepth: 0,
    geography: [],
    devices: { mobile: 0, desktop: 0 },
    languages: []
};

const QuickMetrics = () => {
    const [metrics, setMetrics] = useState<QuickMetricsData>(initialMetrics);
    const [error, setError] = useState('');
    const [initialLoad, setInitialLoad] = useState(true);

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8084/analytics/v1/metrics/fast');

        const handleMetricUpdate = (event: MessageEvent) => {
            try {
                const metricEvent: MetricEvent = JSON.parse(event.data);

                setMetrics(prev => ({
                    ...prev,
                    [metricEvent.type]: metricEvent.data
                }));

                if (initialLoad) setInitialLoad(false);
            } catch (err) {
                setError('Ошибка обработки данных метрики');
            }
        };

        eventSource.addEventListener('message', handleMetricUpdate);

        eventSource.onerror = (err) => {
            setError('Ошибка подключения к метрикам');
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [initialLoad]);

    if (initialLoad) {
        return (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 animate-pulse">
                {Object.keys(initialMetrics).map((_, i) => (
                    <div key={i} className="metric-card">
                        <div className="h-6 bg-gray-200 rounded w-1/3 mb-4"></div>
                        <div className="space-y-2">
                            <div className="h-4 bg-gray-200 rounded w-3/4"></div>
                            <div className="h-4 bg-gray-200 rounded w-1/2"></div>
                        </div>
                    </div>
                ))}
            </div>
        );
    }

    if (error) {
        return <div className="text-red-500 text-center p-4">{error}</div>;
    }

    return (
        <div className="mb-8">
            <h2 className="text-xl mb-4 font-semibold">Быстрые метрики (реальное время)</h2>
            <div className="space-y-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {/* Активные сессии */}
                <div className="metric-card">
                    <h3>Активные сессии</h3>
                    <p>{metrics.activeSessions} пользователей онлайн</p>
                </div>

                {/* Клики */}
                <div className="metric-card">
                    <h3>Клики (последние 5 минут)</h3>
                    <p>Всего: {metrics.clicks.totalLast5Min}</p>
                    <div className="mt-2">
                        <p>Топ элементов:</p>
                        <ul className="list-disc pl-4">
                            {metrics.clicks.topElements.map((item, index) => (
                                <li key={index}>{item.element_id}: {item.count}</li>
                            ))}
                        </ul>
                    </div>
                </div>

                {/* Ошибки */}
                <div className="metric-card">
                    <h3>Ошибки (последние 5 минут)</h3>
                    <div className="mb-2">
                        {Object.entries(metrics.errors.counts).map(([type, count]) => (
                            <p key={type}>{type}: {count}</p>
                        ))}
                    </div>
                    <div className="mt-2">
                        <p>Всплески ошибок:</p>
                        {metrics.errors.spikes.map((spike, index) => (
                            <p key={index}>
                                {spike.browser && `${spike.browser}: ${spike.count}`}
                                {spike.os && `${spike.os}: ${spike.count}`}
                            </p>
                        ))}
                    </div>
                </div>

                {/* Поисковые запросы */}
                <div className="metric-card">
                    <h3>Поисковые запросы (последние 30 минут)</h3>
                    <ul className="list-disc pl-4">
                        {metrics.searchQueries.map((query, index) => (
                            <li key={index}>{query}</li>
                        ))}
                    </ul>
                </div>

                {/* Конверсии */}
                <div className="metric-card">
                    <h3>Конверсии (последний час)</h3>
                    <p>Покупки: {metrics.conversions.purchases}</p>
                    <p>Регистрации: {metrics.conversions.signups}</p>
                    <div className="mt-2">
                        <p>Выручка за день: ${metrics.revenue}</p>
                    </div>
                </div>

                {/* Глубина прокрутки */}
                <div className="metric-card">
                    <h3>Глубина прокрутки</h3>
                    <p>Среднее значение: {metrics.averageScrollDepth}%</p>
                </div>

                {/* География */}
                <div className="metric-card">
                    <h3>Топ-3 локаций</h3>
                    <ul className="list-disc pl-4">
                        {metrics.geography.map((loc, index) => (
                            <li key={index}>
                                {loc.city} ({loc.country}): {loc.users}
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Устройства */}
                <div className="metric-card">
                    <h3>Устройства</h3>
                    <p>Мобильные: {metrics.devices.mobile}%</p>
                    <p>Десктопы: {metrics.devices.desktop}%</p>
                </div>

                {/* Языки */}
                <div className="metric-card">
                    <h3>Языки браузеров</h3>
                    <ul className="list-disc pl-4">
                        {metrics.languages.map((lang, index) => (
                            <li key={index}>{lang.toUpperCase()}</li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default QuickMetrics;