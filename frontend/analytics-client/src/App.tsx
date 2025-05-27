import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useParams } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Metrics from './pages/Metrics';
import Dashboard from './pages/Dashboard';

const DashboardWithUserId = () => {
    const { userId } = useParams<{ userId: string }>();
    return <Dashboard userId={userId!} />;
};

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard/:userId" element={<DashboardWithUserId />} />
                <Route path="/metrics" element={<Metrics />} />
                <Route path="/" element={<Login />} />
            </Routes>
        </Router>
    );
};

export default App;