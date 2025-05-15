import React, {useEffect} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Metrics from './pages/Metrics';

const App = () => {

    // При старте приложения создаем дефолтного пользователя и сайт, если они отсутствуют в LocalStorage
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        const storedSites = localStorage.getItem('sites');

        // Если пользователя нет, создаем дефолтного
        if (!storedUser) {
            const defaultUser = { name: 'Vladislav Fedotov', email: 'vlad@mail.com' };
            localStorage.setItem('user', JSON.stringify(defaultUser));
        }

        // Если нет сайтов, создаем дефолтный сайт
        if (!storedSites) {
            const defaultSite = [{ name: 'My first site', url: 'https://myfirstsite.com' }];
            localStorage.setItem('sites', JSON.stringify(defaultSite));
        }
    }, []);

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/metrics" element={<Metrics />} /> {/* Роут для страницы метрик с ID сайта */}
                <Route path="/" element={<Login />} />
            </Routes>
        </Router>
    );
};

export default App;
