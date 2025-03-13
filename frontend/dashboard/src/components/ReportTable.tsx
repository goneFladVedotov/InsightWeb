// src/components/ReportTable.tsx
import React from 'react';

interface ReportTableProps {
  data: any[];
  columns: string[]; // список полей, которые нужно отобразить
}

function ReportTable({ data, columns }: ReportTableProps) {
  if (!data || !data.length) {
    return <div>Нет данных</div>;
  }

  return (
    <table className="report-table">
      <thead>
        <tr>
          {columns.map((col) => (
            <th key={col}>{col}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row: any, i) => (
          <tr key={i}>
            {columns.map((col) => (
              <td key={col}>{row[col]}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ReportTable;
