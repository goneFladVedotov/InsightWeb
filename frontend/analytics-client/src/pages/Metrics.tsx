import React from 'react';
import { Link } from "react-router-dom";
import QuickMetrics from '../components/QuickMetrics';
import SlowMetrics from '../components/SlowMetrics';

const Metrics = () => {
    const siteName = 'My first site';
    const siteUrl = 'http://myfirstsite.com';

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <div className="mb-8 bg-white p-4 rounded-lg shadow-md">
                <h1 className="text-2xl mb-4 text-center text-gray-800">Метрики сайта</h1>
                <div className="text-center">
                    <p className="text-xl font-semibold">{siteName}</p>
                    <p className="text-blue-600 hover:underline">
                        <a
                            href={siteUrl}
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            {siteUrl}
                        </a>
                    </p>
                </div>
            </div>

            <div className="space-y-8">
                <div className="bg-white p-4 rounded-lg shadow-md">
                    <QuickMetrics />
                </div>

                <div className="bg-white p-4 rounded-lg shadow-md">
                    <SlowMetrics />
                </div>
            </div>

            <div className="mt-8 text-center">
                <Link
                    to="/dashboard-with-sites"
                    className="text-blue-500 hover:text-blue-700 transition-colors inline-flex items-center"
                >
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="h-5 w-5 mr-2"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                    >
                        <path
                            fillRule="evenodd"
                            d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
                            clipRule="evenodd"
                        />
                    </svg>
                    В личный кабинет
                </Link>
            </div>
        </div>
    );
};

export default Metrics;