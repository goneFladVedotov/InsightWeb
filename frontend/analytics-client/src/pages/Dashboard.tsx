import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState<{ name: string; email: string }>({ name: '', email: '' });
    const [sites, setSites] = useState<{ name: string; url: string }[]>([]);
    const [newSiteName, setNewSiteName] = useState('');
    const [newSiteUrl, setNewSiteUrl] = useState('');

    // Загружаем данные пользователя из LocalStorage
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }

        const storedSites = localStorage.getItem('sites');
        if (storedSites) {
            setSites(JSON.parse(storedSites));
        }
    }, []);

    const handleAddSite = () => {
        const newSite = { name: newSiteName, url: newSiteUrl };
        const updatedSites = [...sites, newSite];
        setSites(updatedSites);
        localStorage.setItem('sites', JSON.stringify(updatedSites)); // Сохраняем новые сайты в LocalStorage
        setNewSiteName('');
        setNewSiteUrl('');
    };

    const handleLogout = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('sites');
        navigate('/login');
    };

    const handleDeleteSite = (index: number) => {
        const updatedSites = sites.filter((_, i) => i !== index);
        setSites(updatedSites);
        localStorage.setItem('sites', JSON.stringify(updatedSites)); // Удаляем сайт из LocalStorage
    };

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <h1 className="text-2xl mb-4 text-center text-gray-800">Личный кабинет</h1>
            <div className="mb-8">
                <p className="text-xl">Имя пользователя: {user.name}</p>
                <p className="text-xl">Email: {user.email}</p>
            </div>

            {/* Список сайтов */}
            <div className="mb-8">
                <h2 className="text-xl mb-4">Ваши сайты</h2>
                {sites.length === 0 ? (
                    <p>Нет сайтов, добавьте новый сайт.</p>
                ) : (
                    <div className="space-y-4">
                        {sites.map((site, index) => (
                            <div key={index} className="bg-white p-4 rounded-lg shadow-md">
                                <p className="font-bold">{site.name}</p>
                                <p>{site.url}</p>
                                <button onClick={() => handleDeleteSite(index)} className="text-red-500 mt-2">Удалить</button>
                                <Link to={`/metrics/${index}`} className="text-blue-500 mt-2">Посмотреть метрики</Link>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            {/* Форма добавления нового сайта */}
            <div className="mb-8">
                <h3 className="text-lg mb-2">Добавить новый сайт</h3>
                <input
                    type="text"
                    value={newSiteName}
                    onChange={(e) => setNewSiteName(e.target.value)}
                    placeholder="Введите название сайта"
                    className="border border-gray-300 p-2 mb-4 w-full rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <input
                    type="url"
                    value={newSiteUrl}
                    onChange={(e) => setNewSiteUrl(e.target.value)}
                    placeholder="Введите URL сайта"
                    className="border border-gray-300 p-2 mb-4 w-full rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <button onClick={handleAddSite} className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 transition-colors">
                    Добавить сайт
                </button>
            </div>

            {/* Кнопка выхода */}
            <div className="mt-4 text-center">
                <button onClick={handleLogout} className="bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600 transition-colors">
                    Выйти из аккаунта
                </button>
            </div>
        </div>
    );
};

export default Dashboard;
