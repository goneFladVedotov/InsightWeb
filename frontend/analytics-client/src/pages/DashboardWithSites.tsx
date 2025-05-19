import React, { useState } from 'react';
import { Link } from 'react-router-dom';

interface Site {
    id: string;
    name: string;
    url: string;
}

const DashboardWithSites = () => {
    const user = { name: 'Иван Иванов', email: 'ivan@example.com' };
    const [sites, setSites] = useState<Site[]>([{
        id: Math.random().toString(36).substr(2, 9),
        name: 'Сайт 1',
        url: 'https://site1.com'
    }]);

    const [newSiteName, setNewSiteName] = useState('');
    const [newSiteUrl, setNewSiteUrl] = useState('');

    const handleAddSite = (e: React.FormEvent) => {
        e.preventDefault();

        if (!newSiteUrl.startsWith('http://') && !newSiteUrl.startsWith('https://')) {
            alert('URL должен начинаться с http:// или https://');
            return;
        }

        if (!newSiteName.trim() || !newSiteUrl.trim()) {
            alert('Заполните все поля');
            return;
        }

        const newSite: Site = {
            id: Math.random().toString(36).substr(2, 9),
            name: newSiteName.trim(),
            url: newSiteUrl.trim()
        };

        setSites([...sites, newSite]);
        setNewSiteName('');
        setNewSiteUrl('');
    };

    const handleDeleteSite = (siteId: string) => {
        setSites(sites.filter(site => site.id !== siteId));
    };

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <h1 className="text-2xl mb-4 text-center text-gray-800">Личный кабинет</h1>

            <div className="mb-8 bg-white p-4 rounded-lg shadow-md">
                <p className="text-xl">Имя пользователя: {user.name}</p>
                <p className="text-xl">Email: {user.email}</p>
            </div>

            <div className="mb-8">
                <h2 className="text-xl mb-4">Ваши сайты</h2>
                <div className="space-y-4">
                    {sites.map((site) => (
                        <div key={site.id} className="bg-white p-4 rounded-lg shadow-md">
                            <div className="flex justify-between items-start">
                                <div>
                                    <p className="font-bold text-lg">{site.name}</p>
                                    <p className="text-blue-600 hover:underline">
                                        <a href={site.url} target="_blank" rel="noopener noreferrer">
                                            {site.url}
                                        </a>
                                    </p>
                                </div>
                                <div className="flex gap-2">
                                    <button
                                        onClick={() => handleDeleteSite(site.id)}
                                        className="text-red-500 hover:text-red-700 transition-colors"
                                    >
                                        Удалить
                                    </button>
                                    <Link
                                        to={`/metrics`}
                                        className="text-blue-500 hover:text-blue-700 transition-colors"
                                    >
                                        Метрики
                                    </Link>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <form onSubmit={handleAddSite} className="bg-white p-4 rounded-lg shadow-md">
                <h3 className="text-lg mb-4 font-semibold">Добавить новый сайт</h3>

                <div className="mb-4">
                    <label className="block mb-2 text-gray-700">
                        Название сайта:
                        <input
                            type="text"
                            value={newSiteName}
                            onChange={(e) => setNewSiteName(e.target.value)}
                            placeholder="Мой сайт"
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm
                                    focus:border-blue-500 focus:ring-blue-500"
                            required
                        />
                    </label>
                </div>

                <div className="mb-4">
                    <label className="block mb-2 text-gray-700">
                        URL сайта:
                        <input
                            type="url"
                            value={newSiteUrl}
                            onChange={(e) => setNewSiteUrl(e.target.value)}
                            placeholder="https://example.com"
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm
                                    focus:border-blue-500 focus:ring-blue-500"
                            pattern="https?://.+"
                            required
                        />
                    </label>
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold
                            py-2 px-4 rounded-md transition-colors"
                >
                    Добавить сайт
                </button>
            </form>

            <div className="mt-8 text-center">
                <Link
                    to="/login"
                    className="text-red-500 hover:text-red-700 transition-colors"
                >
                    ← Выйти из аккаунта
                </Link>
            </div>
        </div>
    );
};

export default DashboardWithSites;