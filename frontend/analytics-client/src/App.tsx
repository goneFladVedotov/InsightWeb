import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Metrics from './pages/Metrics';
import DashboardWithSites from './pages/DashboardWithSites';
import DashboardWithoutSites from './pages/DashboardWithoutSites';

// HOC для задержки рендеринга
const DelayedComponent = ({ component: Component }: { component: React.ComponentType }) => {
    const [isDelayed, setIsDelayed] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsDelayed(true);
        }, 1000);

        return () => clearTimeout(timer);
    }, []);

    return isDelayed ? <Component /> : null;
};

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<DelayedComponent component={Login} />} />
                <Route path="/register" element={<DelayedComponent component={Register} />} />
                <Route path="/dashboard" element={<DelayedComponent component={Dashboard} />} />
                <Route
                    path="/dashboard-with-sites"
                    element={<DelayedComponent component={DashboardWithSites} />}
                />
                <Route
                    path="/dashboard-without-sites"
                    element={<DelayedComponent component={DashboardWithoutSites} />}
                />
                <Route path="/metrics" element={<DelayedComponent component={Metrics} />} />
                <Route path="/" element={<DelayedComponent component={Login} />} />
            </Routes>
        </Router>
    );
};

export default App;