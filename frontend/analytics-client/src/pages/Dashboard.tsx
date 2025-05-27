import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

interface Site {
    id: string;
    name: string;
    url: string;
}

interface User {
    id: string;
    name: string;
    email: string;
}

interface Props {
    userId: string;
}

const Dashboard: React.FC<Props> = ({ userId }) => {
    const [sites, setSites] = useState<Site[]>([]);
    const [user, setUser] = useState<User | null>(null);
    const [newSiteName, setNewSiteName] = useState('');
    const [newSiteUrl, setNewSiteUrl] = useState('');
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    // Загрузка данных пользователя и сайтов
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Загрузка данных пользователя
                const userResponse = await axios.get(`http://localhost:8083/auth/v1/user/${userId}`);
                setUser(userResponse.data);

                // Загрузка сайтов пользователя
                const sitesResponse = await axios.get(`http://localhost:8083/auth/v1/site/${userId}`);
                setSites(sitesResponse.data);
            } catch (err) {
                setError('Ошибка загрузки данных');
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, [userId]);

    const handleAddSite = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!newSiteUrl.startsWith('http://') && !newSiteUrl.startsWith('https://')) {
            alert('URL должен начинаться с http:// или https://');
            return;
        }

        if (!newSiteName.trim() || !newSiteUrl.trim()) {
            alert('Заполните все поля');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8083/auth/v1/site', {
                userId,
                name: newSiteName.trim(),
                url: newSiteUrl.trim()
            });

            setSites([...sites, response.data]);
            setNewSiteName('');
            setNewSiteUrl('');
        } catch (err) {
            alert('Ошибка при добавлении сайта');
        }
    };

    const handleDeleteSite = async (siteId: string) => {
        try {
            await axios.delete(`http://localhost:8083/auth/v1/site/${siteId}`, {
                data: { userId } // Для DELETE с body в axios
            });
            setSites(sites.filter(site => site.id !== siteId));
        } catch (err) {
            alert('Ошибка при удалении сайта');
        }
    };

    if (isLoading) {
        return (
            <div className="p-8 text-center">
                <div className="animate-pulse">
                    <div className="h-8 bg-gray-200 rounded w-1/4 mb-4 mx-auto"></div>
                    <div className="space-y-4">
                        {[...Array(3)].map((_, i) => (
                            <div key={i} className="h-16 bg-gray-200 rounded-lg"></div>
                        ))}
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="p-8 text-center text-red-500">
                {error}
                <div className="mt-4">
                    <Link
                        to="/login"
                        className="text-blue-500 hover:text-blue-700"
                    >
                        Попробовать снова
                    </Link>
                </div>
            </div>
        );
    }

    if (!user) {
        return <div className="p-8 text-center">Пользователь не найден</div>;
    }

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <h1 className="text-2xl mb-4 text-center text-gray-800">Личный кабинет</h1>

            <div className="mb-8 bg-white p-4 rounded-lg shadow-md">
                <p className="text-xl">Имя пользователя: {user.name}</p>
                <p className="text-xl">Email: {user.email}</p>
                <p className="text-sm text-gray-500 mt-2">ID: {user.id}</p>
            </div>

            <div className="mb-8">
                <h2 className="text-xl mb-4">Ваши сайты</h2>
                <div className="space-y-4">
                    {sites.length === 0 ? (
                        <div className="bg-white p-4 rounded-lg shadow-md text-gray-500">
                            Нет добавленных сайтов
                        </div>
                    ) : (
                        sites.map((site) => (
                            <div key={site.id} className="bg-white p-4 rounded-lg shadow-md">
                                <div className="flex justify-between items-start">
                                    <div>
                                        <p className="font-bold text-lg">{site.name}</p>
                                        <p className="text-blue-600 hover:underline">
                                            <a
                                                href={site.url}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
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
                                            to={`/metrics/${site.id}`}
                                            className="text-blue-500 hover:text-blue-700 transition-colors"
                                        >
                                            Метрики
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        ))
                    )}
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
                    onClick={() => localStorage.removeItem('authToken')}
                >
                    ← Выйти из аккаунта
                </Link>
            </div>
        </div>
    );
};

export default Dashboard;