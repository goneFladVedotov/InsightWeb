import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div className="p-8">
            <h1 className="text-2xl mb-4">Главная страница</h1>
            {/* Блоки с сайтами */}
            <div className="flex flex-wrap gap-4">
                <div className="w-1/4 bg-gray-100 p-4 rounded-lg">
                    <p>Сайт 1</p>
                    {/* Ссылка на страницу с метриками */}
                    <Link to="/metrics" className="text-blue-500">Посмотреть метрики</Link>
                </div>
                <div className="w-1/4 bg-gray-100 p-4 rounded-lg">
                    <p>Сайт 2</p>
                    <Link to="/metrics" className="text-blue-500">Посмотреть метрики</Link>
                </div>
            </div>
            <div className="mt-4">
                <Link to="/dashboard" className="text-blue-500">Перейти в личный кабинет</Link>
            </div>
        </div>
    );
};

export default Home;