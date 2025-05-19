import React from 'react';

const QuickMetrics = () => {
    // Статичные данные для быстрых метрик
    const activeSessions = 10;

    const clicks = {
        totalLast5Min: 30,
        topElements: [
            { element_id: 'cta-button', count: 12 },
            { element_id: 'search-icon', count: 8 },
            { element_id: 'product-image', count: 5 }
        ]
    };

    const errors = {
        counts: { jsError: 5, http404: 2, networkError: 3 },
        spikes: [
            { browser: 'Chrome', count: 8 },
            { os: 'iOS', count: 4 }
        ]
    };

    const searchQueries = ['buy shoes', 'best laptop', 'how to code'];
    const conversions = { purchases: 7, signups: 5 };
    const revenue = 2000;
    const averageScrollDepth = 68;

    const geography = [
        { city: 'Moscow', country: 'Russia', users: 3 },
        { city: 'New-York', country: 'USA', users: 1 },
        { city: 'Istanbul', country: 'Turkey', users: 2 }
    ];

    const devices = {
        mobile: 0,
        desktop: 2
    };

    const languages = ['ru'];

    return (
        <div className="mb-8">
            <h2 className="text-xl mb-4 font-semibold">Быстрые метрики</h2>
            <div className="space-y-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {/* Активные сессии */}
                <div className="metric-card">
                    <h3>Активные сессии</h3>
                    <p>{activeSessions} пользователей онлайн</p>
                </div>

                {/* Клики */}
                <div className="metric-card">
                    <h3>Клики (последние 5 минут)</h3>
                    <p>Всего: {clicks.totalLast5Min}</p>
                    <div className="mt-2">
                        <p>Топ элементов:</p>
                        <ul className="list-disc pl-4">
                            {clicks.topElements.map((item, index) => (
                                <li key={index}>{item.element_id}: {item.count}</li>
                            ))}
                        </ul>
                    </div>
                </div>

                {/* Ошибки */}
                <div className="metric-card">
                    <h3>Ошибки (последние 5 минут)</h3>
                    <div className="mb-2">
                        {Object.entries(errors.counts).map(([type, count]) => (
                            <p key={type}>{type}: {count}</p>
                        ))}
                    </div>
                    <div className="mt-2">
                        <p>Всплески ошибок:</p>
                        {errors.spikes.map((spike, index) => (
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
                        {searchQueries.map((query, index) => (
                            <li key={index}>{query}</li>
                        ))}
                    </ul>
                </div>

                {/* Конверсии */}
                <div className="metric-card">
                    <h3>Конверсии (последний час)</h3>
                    <p>Покупки: {conversions.purchases}</p>
                    <p>Регистрации: {conversions.signups}</p>
                    <div className="mt-2">
                        <p>Выручка за день: ${revenue}</p>
                    </div>
                </div>

                {/* Глубина прокрутки */}
                <div className="metric-card">
                    <h3>Глубина прокрутки</h3>
                    <p>Среднее значение: {averageScrollDepth}%</p>
                </div>

                {/* География */}
                <div className="metric-card">
                    <h3>Топ-3 локаций</h3>
                    <ul className="list-disc pl-4">
                        {geography.map((loc, index) => (
                            <li key={index}>
                                {loc.city} ({loc.country}): {loc.users}
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Устройства */}
                <div className="metric-card">
                    <h3>Устройства</h3>
                    <p>Мобильные: {devices.mobile}%</p>
                    <p>Десктопы: {devices.desktop}%</p>
                </div>

                {/* Языки */}
                <div className="metric-card">
                    <h3>Языки браузеров</h3>
                    <ul className="list-disc pl-4">
                        {languages.map((lang, index) => (
                            <li key={index}>{lang.toUpperCase()}</li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default QuickMetrics;