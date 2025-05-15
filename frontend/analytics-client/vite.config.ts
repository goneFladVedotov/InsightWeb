import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0',  // Это позволяет доступ с любого IP, для разработки
    port: 3000,       // Указываем желаемый порт
    strictPort: true, // Принудительное использование указанного порта (если порт занят — приложение не запускается)
    open: true,       // Открыть браузер автоматически при запуске сервера
  }
})
