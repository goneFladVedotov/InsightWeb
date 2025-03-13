// src/components/Charts/TrafficChart.tsx
import React from 'react';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title } from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title
);

interface TrafficChartProps {
  chartData: {
    labels?: string[];
    data?: number[];
  };
}

function TrafficChart({ chartData }: TrafficChartProps) {
  const labels = chartData.labels || [];
  const dataValues = chartData.data || [];

  const data = {
    labels,
    datasets: [
      {
        label: 'Просмотры',
        data: dataValues,
        borderColor: 'blue',
        fill: false
      }
    ]
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: false,
        text: 'Трафик по дням'
      }
    }
  };

  return <Line data={data} options={options} />;
}

export default TrafficChart;
