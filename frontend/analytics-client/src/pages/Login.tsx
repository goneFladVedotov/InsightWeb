import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        setIsLoading(true);

        try {
            const response = await axios.post('http://localhost:8083/auth/v1/login', {
                email,
                password
            });

            // Сохраняем JWT токен и данные пользователя
            localStorage.setItem('authToken', response.data.token);
            localStorage.setItem('user', JSON.stringify(response.data.user));

            // Перенаправляем на целевую страницу
            navigate('/dashboard-with-sites');
        } catch (err) {
            if (axios.isAxiosError(err)) {
                setError(err.response?.data?.message || 'Ошибка аутентификации');
            } else {
                setError('Произошла непредвиденная ошибка');
            }
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <form onSubmit={handleSubmit} className="bg-white p-8 rounded-lg shadow-lg w-full max-w-sm">
                <h1 className="text-2xl mb-4 text-center text-gray-800">Вход в систему</h1>

                <div className="mb-4">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                        disabled={isLoading}
                    />
                </div>

                <div className="mb-4">
                    <input
                        type="password"
                        placeholder="Пароль"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                        disabled={isLoading}
                    />
                </div>

                {error && <p className="text-red-500 text-center mb-4">{error}</p>}

                <button
                    type="submit"
                    className="bg-blue-500 text-white py-2 px-4 w-full rounded-lg hover:bg-blue-600 transition-colors disabled:opacity-50"
                    disabled={isLoading}
                >
                    {isLoading ? 'Вход...' : 'Войти'}
                </button>

                <div className="mt-4 text-center">
                    <p>
                        Нет аккаунта? {' '}
                        <a href="/register" className="text-blue-500 hover:underline">
                            Зарегистрируйтесь
                        </a>
                    </p>
                </div>
            </form>
        </div>
    );
};

export default Login;