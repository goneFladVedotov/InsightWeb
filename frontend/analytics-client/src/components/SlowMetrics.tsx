import React from 'react';
import { Line, Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend } from 'chart.js';

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

const SlowMetrics = () => {
    // Статичные данные
    const sessionData = {
        daily: [4, 5, 6, 3, 4, 5, 4],
        byDevice: { mobile: 3.2, desktop: 5.1 }
    };

    const geographyData = {
        countries: [
            { country: 'Россия', users: 3, conversions: 11 },
            { country: 'США', users: 2, conversions: 6 },
            { country: 'Германия', users: 1, conversions: 8 }
        ]
    };

    const screenData = {
        topResolutions: ['1920x1080', '1440x900', '375x812', '1366x768', '414x896'],
        quarterlyTrend: [45, 52, 48, 55]
    };

    const browserData = {
        shares: { Chrome: 65, Safari: 25, Firefox: 7, Edge: 3 },
        osErrors: { Windows: 15, macOS: 8, iOS: 5, Android: 3 }
    };

    const revenueData = {
        monthly: [4200, 7350, 5800, 9200],
        averageCheck: { purchase: 85, signup: 0 }
    };

    const conversionsData = {
        dailyTrend: [2, 5, 15, 7, 12],
        comparison: { purchase: 45, signup: 32 }
    };

    const searchData = {
        avgResults: 5,
        zeroResultQueries: ['unicorn', 'time machine', 'free bitcoin']
    };

    const scrollData = {
        deepScroll: 4,
        deviceComparison: { mobile: 29, desktop: 47 }
    };

    const clicksData = {
        topElements: ['cta-button', 'search', 'logo'],
        timeEffectiveness: [12, 45, 32, 18]
    };

    const errorData = {
        types: { js_error: 20, http_404: 10, timeout: 3 },
        weeklyTrend: [5, 8, 4, 5, 7, 6, 6]
    };

    // Генератор графиков
    const generateChartData = (type: 'line' | 'bar', label: string, data: number[], labels?: string[]) => ({
        labels: labels || data.map((_, i) => `Day ${i + 1}`),
        datasets: [{
            label,
            data,
            borderColor: type === 'line' ? 'rgb(75, 192, 192)' : 'rgb(54, 162, 235)',
            backgroundColor: type === 'bar' ? 'rgba(54, 162, 235, 0.2)' : undefined,
            tension: 0.1
        }]
    });

    return (
        <div className="mb-8">
            <h2 className="text-xl mb-4 font-semibold">Аналитика</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {/* Средняя продолжительность сессий */}
                <div className="metric-card">
                    <h3>Длительность сессий</h3>
                    <Line data={generateChartData('line', 'Минуты', sessionData.daily)} />
                    <div className="mt-4">
                        <p>Мобильные: {sessionData.byDevice.mobile} мин</p>
                        <p>Десктопы: {sessionData.byDevice.desktop} мин</p>
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
                        {geographyData.countries.map((item, index) => (
                            <tr key={index} className="border-b">
                                <td className="py-2">{item.country}</td>
                                <td>{item.users}</td>
                                <td>{item.conversions}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Разрешения экранов */}
                <div className="metric-card">
                    <h3>Разрешения экранов</h3>
                    <ul className="list-disc pl-4 mb-4">
                        {screenData.topResolutions.map((res, index) => (
                            <li key={index}>{res}</li>
                        ))}
                    </ul>
                    <Line data={generateChartData('line', 'Динамика', screenData.quarterlyTrend, ['Q1', 'Q2', 'Q3', 'Q4'])} />
                </div>

                {/* Браузеры и ОС */}
                <div className="metric-card">
                    <h3>Браузеры/ОС</h3>
                    <Bar data={generateChartData('bar', 'Доля %', Object.values(browserData.shares), Object.keys(browserData.shares))} />
                    <div className="mt-4">
                        <p>Ошибки по ОС:</p>
                        {Object.entries(browserData.osErrors).map(([os, count]) => (
                            <p key={os}>{os}: {count}%</p>
                        ))}
                    </div>
                </div>

                {/* Выручка */}
                <div className="metric-card">
                    <h3>Выручка</h3>
                    <Line data={generateChartData('line', 'USD', revenueData.monthly, ['Jan', 'Feb', 'Mar', 'Apr'])} />
                    <div className="mt-4">
                        <p>Средний чек:</p>
                        <p>Покупки: ${revenueData.averageCheck.purchase}</p>
                        <p>Регистрации: ${revenueData.averageCheck.signup}</p>
                    </div>
                </div>

                {/* Тренды конверсий */}
                <div className="metric-card">
                    <h3>Конверсии</h3>
                    <Line data={generateChartData('line', 'Конверсии/день', conversionsData.dailyTrend)} />
                    <div className="mt-4">
                        <Bar data={{
                            labels: ['Покупки', 'Регистрации'],
                            datasets: [{
                                label: 'Сравнение',
                                data: Object.values(conversionsData.comparison),
                                backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)']
                            }]
                        }} />
                    </div>
                </div>

                {/* Поисковая аналитика */}
                <div className="metric-card">
                    <h3>Поисковые запросы</h3>
                    <p>Среднее результатов: {searchData.avgResults}</p>
                    <div className="mt-2">
                        <p>Нулевые результаты:</p>
                        <ul className="list-disc pl-4">
                            {searchData.zeroResultQueries.map((q, i) => (
                                <li key={i}>{q}</li>
                            ))}
                        </ul>
                    </div>
                </div>

                {/* Прокрутка страниц */}
                <div className="metric-card">
                    <h3>Прокрутка</h3>
                    <p>Глубокая прокрутка: {scrollData.deepScroll}%</p>
                    <div className="mt-2">
                        <p>Мобильные: {scrollData.deviceComparison.mobile}%</p>
                        <p>Десктопы: {scrollData.deviceComparison.desktop}%</p>
                    </div>
                </div>

                {/* Кликабельность */}
                <div className="metric-card">
                    <h3>Кликабельность</h3>
                    <ul className="list-disc pl-4 mb-2">
                        {clicksData.topElements.map((el, i) => (
                            <li key={i}>{el}</li>
                        ))}
                    </ul>
                    <Line data={generateChartData('line', 'Активность', clicksData.timeEffectiveness, ['Ночь', 'Утро', 'День', 'Вечер'])} />
                </div>

                {/* Ошибки */}
                <div className="metric-card">
                    <h3>Ошибки</h3>
                    <div className="mb-2">
                        {Object.entries(errorData.types).map(([type, count]) => (
                            <p key={type}>{type}: {count}</p>
                        ))}
                    </div>
                    <Line data={generateChartData('line', 'Недельная динамика', errorData.weeklyTrend)} />
                </div>
            </div>
        </div>
    );
};

export default SlowMetrics;