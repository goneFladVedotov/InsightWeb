import React, { useEffect, useState } from 'react';
import { Line, Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend } from 'chart.js';
import axios from 'axios';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    BarElement,
    Title,
    Tooltip,
    Legend
);

interface MetricsData {
    sessionData: {
        daily: number[];
        byDevice: { mobile: number; desktop: number };
    };
    geographyData: {
        countries: Array<{ country: string; users: number; conversions: number }>;
    };
    screenData: {
        topResolutions: string[];
        quarterlyTrend: number[];
    };
    browserData: {
        shares: Record<string, number>;
        osErrors: Record<string, number>;
    };
    revenueData: {
        monthly: number[];
        averageCheck: { purchase: number; signup: number };
    };
    conversionsData: {
        dailyTrend: number[];
        comparison: { purchase: number; signup: number };
    };
    searchData: {
        avgResults: number;
        zeroResultQueries: string[];
    };
    scrollData: {
        deepScroll: number;
        deviceComparison: { mobile: number; desktop: number };
    };
    clicksData: {
        topElements: string[];
        timeEffectiveness: number[];
    };
    errorData: {
        types: Record<string, number>;
        weeklyTrend: number[];
    };
}

const SlowMetrics = () => {
    const [metrics, setMetrics] = useState<MetricsData | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchMetrics = async () => {
            try {
                const response = await axios.get('http://localhost:8084/analytics/v1/metrics/slow');
                setMetrics(response.data);
            } catch (err) {
                setError('Ошибка загрузки метрик');
            } finally {
                setLoading(false);
            }
        };

        fetchMetrics();
    }, []);

    const generateChartData = (
        type: 'line' | 'bar',
        label: string,
        data: number[],
        labels?: string[]
    ) => ({
        labels: labels || data.map((_, i) => {
            const date = new Date();
            date.setDate(date.getDate() - (data.length - 1 - i));
            return date.toLocaleDateString('ru-RU', { day: '2-digit', month: '2-digit' });
        }),
        datasets: [{
            label,
            data,
            borderColor: type === 'line' ? 'rgb(75, 192, 192)' : 'rgb(54, 162, 235)',
            backgroundColor: type === 'bar' ? 'rgba(54, 162, 235, 0.2)' : undefined,
            tension: 0.1
        }]
    });

    if (loading) {
        return (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 animate-pulse">
                {[...Array(9)].map((_, i) => (
                    <div key={i} className="metric-card">
                        <div className="h-6 bg-gray-200 rounded w-1/3 mb-4"></div>
                        <div className="h-48 bg-gray-200 rounded"></div>
                    </div>
                ))}
            </div>
        );
    }

    if (error) {
        return <div className="text-red-500 text-center p-4">{error}</div>;
    }

    if (!metrics) {
        return <div className="text-center p-4">Данные не найдены</div>;
    }

    return (
        <div className="mb-8">
            <h2 className="text-xl mb-4 font-semibold">Аналитика</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {/* Средняя продолжительность сессий */}
                <div className="metric-card">
                    <h3>Длительность сессий</h3>
                    <Line data={generateChartData('line', 'Минуты', metrics.sessionData.daily)} />
                    <div className="mt-4">
                        <p>Мобильные: {metrics.sessionData.byDevice.mobile} мин</p>
                        <p>Десктопы: {metrics.sessionData.byDevice.desktop} мин</p>
                    </div>
                </div>

                {/* Географический анализ */}
                <div className="metric-card">
                    <h3>География (месяц)</h3>
                    <table className="w-full">
                        <thead>
                        <tr className="text-left border-b">
                            <th>Страна</th>
                            <th>Пользователи</th>
                            <th>Конверсии</th>
                        </tr>
                        </thead>
                        <tbody>
                        {metrics.geographyData.countries.map((item, index) => (
                            <tr key={index} className="border-b">
                                <td className="py-2">{item.country}</td>
                                <td>{item.users}</td>
                                <td>{item.conversions}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Остальные блоки метрик аналогично с использованием metrics */}
                {/* ... */}

            </div>
        </div>
    );
};

export default SlowMetrics;