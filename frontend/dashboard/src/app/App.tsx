// src/App.tsx
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import AppRouter from './router/AppRouter';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <header className="app-header">
          <h1>InsightWeb Dashboard</h1>
          <nav className="app-nav">
            <a href="/">Главная</a> | <a href="/reports">Отчёты</a> | <a href="/settings">Настройки</a>
          </nav>
        </header>
        <main className="app-main">
          <AppRouter />
        </main>
        <footer className="app-footer">
          © {new Date().getFullYear()} InsightWeb
        </footer>
      </div>
    </BrowserRouter>
  );
}

export default App;
