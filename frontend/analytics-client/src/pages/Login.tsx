import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Очищаем предыдущие ошибки
        setError('');

        // Проверка существования email в системе
        if (email === 'qwerty@qw.com') {
            localStorage.setItem('user', JSON.stringify({ email }));
            navigate('/dashboard-without-sites');
            return;
        }

        if (email === 'asdf@as.com') {
            localStorage.setItem('user', JSON.stringify({ email }));
            navigate('/dashboard-with-sites');
            return;
        }

        // Если email не распознан
        setError('Такой почты не существует');
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
                    />
                </div>

                {error && <p className="text-red-500 text-center mb-4">{error}</p>}

                <button
                    type="submit"
                    className="bg-blue-500 text-white py-2 px-4 w-full rounded-lg hover:bg-blue-600 transition-colors"
                >
                    Войти
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