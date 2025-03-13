(function (window) {
  // Внутренняя переменная — конфиг трекера
  let config = {
    endpoint: '',
    siteId: '',
    trackPageViews: false,
    trackClicks: false,
    debug: false
  };

  // Очередь событий на отправку (если хотим копить их и отправлять батчами — можно доработать)
  let eventQueue = [];

  // Флаг, показывающий, инициализирован ли трекер
  let initialized = false;

  // Основная функция, доступная глобально как InsightWeb(...)
  function InsightWeb(command, arg1, arg2) {
    switch (command) {
      case 'init':
        initTracker(arg1);
        break;
      case 'trackEvent':
        trackCustomEvent(arg1, arg2);
        break;
      default:
        logDebug("Unknown command:", command);
    }
  }

  // Инициализация трекера
  function initTracker(userConfig) {
    if (initialized) {
      logDebug("Tracker already initialized");
      return;
    }
    initialized = true;

    // Сливаем пользовательские настройки с дефолтными
    config = Object.assign({}, config, userConfig);

    if (config.debug) {
      logDebug("InsightWeb tracker initialized with config:", config);
    }

    // Если требуется, отслеживаем просмотры страниц
    if (config.trackPageViews) {
      sendEvent('pageView', {
        url: location.href,
        referrer: document.referrer
      });
    }

    // Если требуется, отслеживаем клики
    if (config.trackClicks) {
      document.addEventListener('click', onDocumentClick, true);
    }
  }

  // Обработчик кликов (если trackClicks = true)
  function onDocumentClick(event) {
    const target = event.target;
    // Стараемся собрать полезную информацию
    let tagName = target.tagName;
    let text = (target.innerText || '').trim();
    let clickData = {
      tagName,
      text: text.slice(0, 50), // обрезаем до 50 символов
      url: location.href
    };
    // Отправляем событие
    sendEvent('click', clickData);
  }

  // Трекинг кастомного события
  function trackCustomEvent(eventName, data) {
    sendEvent(eventName, data || {});
  }

  // Утилита для отправки события на сервер
  function sendEvent(eventType, data) {
    if (!initialized) {
      // Ещё не инициализировано, складываем в очередь (можно дорабатывать)
      eventQueue.push({ eventType, data });
      return;
    }

    // Собираем payload
    const payload = {
      siteId: config.siteId,
      eventType: eventType,
      timestamp: Date.now(),
      ...data
    };

    // Логируем (в debug-режиме)
    logDebug("Sending event:", payload);

    // Отправляем запрос на сервер (fetch)
    // Предполагаем, что endpoint = 'https://api.insightweb.com/collect'
    fetch(config.endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    }).catch(err => {
      logDebug("Error sending event:", err);
      // Здесь можно реализовать повторную отправку или сохранение в localStorage
    });
  }

  // Функция для вывода debug-сообщений
  function logDebug(...args) {
    if (config.debug) {
      console.log('[InsightWeb DEBUG]', ...args);
    }
  }

  // Делаем функцию глобально доступной
  // Если InsightWeb уже определён, сохраняем ссылку (на случай коллизий)
  const oldInsightWeb = window.InsightWeb;
  window.InsightWeb = function(command, arg1, arg2) {
    InsightWeb(command, arg1, arg2);
  };

  // Предусматриваем метод .noConflict(), если нужно вернуть старое значение
  window.InsightWeb.noConflict = function() {
    window.InsightWeb = oldInsightWeb;
    return InsightWeb;
  };

})(window);
