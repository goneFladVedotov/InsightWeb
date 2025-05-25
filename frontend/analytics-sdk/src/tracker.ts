export enum DeviceType {
  MOBILE = "MOBILE",
  TABLET = "TABLET",
  DESKTOP = "DESKTOP",
}

export interface TrackingEvent {
  event: string;
  timestamp: number;
  sessionId: string;
  url: string;
  referrer: string;
  ip: string;
  deviceType: DeviceType;
  browser: string;
  os: string;
  language: string;
}

export interface ClickEvent extends TrackingEvent { event: "CLICK"; elementId: string; elementText?: string; }
export interface ConversionEvent extends TrackingEvent { event: "CONVERSION"; type: string; value?: number; }
export interface ErrorEvent extends TrackingEvent { event: "ERROR"; errorType: string; message?: string; }
export interface PageViewEvent extends TrackingEvent { event: "PAGE_VIEW"; title: string; screenWidth?: number; }
export interface ScrollEvent extends TrackingEvent { event: "SCROLL"; depth: number; }
export interface SearchEvent extends TrackingEvent { event: "SEARCH"; query: string; resultsCount?: number; }
export interface SessionStartEvent extends TrackingEvent { event: "SESSION_START"; }
export interface SessionEndEvent extends TrackingEvent { event: "SESSION_END"; durationMs: number; }

export type AnyEvent = ClickEvent | ConversionEvent | ErrorEvent | PageViewEvent | ScrollEvent | SearchEvent | SessionStartEvent | SessionEndEvent;

export interface SDKConfig {
  endpoint: string;
  bufferKey?: string;
  batchSize?: number;
  flushIntervalMs?: number;
  disableTracking?: boolean;
}

export class Tracker {
  private endpoint: string;
  private bufferKey: string;
  private batchSize: number;
  private flushIntervalMs: number;
  private buffer: AnyEvent[] = [];
  private flushTimer?: number;
  private sessionIdFallback?: string;

  constructor(private config: SDKConfig) {
    this.endpoint = config.endpoint;
    this.bufferKey = config.bufferKey ?? "analytics_buffer";
    this.batchSize = config.batchSize ?? 10;
    this.flushIntervalMs = config.flushIntervalMs ?? 10000;

    if (config.disableTracking) return;

    this.loadBuffer();
    this.setupFlushTimer();
    this.trackSessionStart();
    window.addEventListener("beforeunload", () => this.trackSessionEnd());
  }

  private setupFlushTimer() {
    this.flushTimer = window.setInterval(() => this.flush(), this.flushIntervalMs);
  }

  private loadBuffer() {
    try {
      const data = localStorage.getItem(this.bufferKey);
      if (data) this.buffer = JSON.parse(data);
    } catch {
      this.buffer = [];
    }
  }

  private saveBuffer() {
    try {
      localStorage.setItem(this.bufferKey, JSON.stringify(this.buffer));
    } catch (e) {
      console.error("Failed to save buffer", e);
    }
  }

  private enqueue(event: AnyEvent) {
    this.buffer.push(event);
    this.saveBuffer();
    if (event.event === "ERROR" || this.buffer.length >= this.batchSize) {
      this.flush();
    }
  }

  private async flush() {
    if (!navigator.onLine || this.buffer.length === 0) return;
    const batch = [...this.buffer];
    try {
      const response = await fetch(this.endpoint, {
        method: "POST",
        mode: 'no-cors',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(batch),
      });
      if (response.ok) {
        this.buffer = this.buffer.slice(batch.length);
        this.saveBuffer();
      }
    } catch {
      // Keep buffer for retry
    }
  }

  private getDefaultFields(): Omit<TrackingEvent, 'event' | 'timestamp'> & { timestamp: number } {
    return {
      timestamp: Date.now(),
      sessionId: this.getSessionId(),
      url: window.location.href,
      referrer: document.referrer,
      ip: '<collected-server-side>',
      deviceType: this.getDeviceType(),
      browser: navigator.userAgent,
      os: this.getOS(),
      language: navigator.language,
    };
  }

  private getSessionId(): string {
    try {
      let id = sessionStorage.getItem("analytics_session");
      if (!id) {
        try {
          id = crypto.randomUUID();
        } catch (e) {
          id = Math.random().toString(36).substring(2) + Date.now().toString(36);
        }
        sessionStorage.setItem("analytics_session", id);
        sessionStorage.setItem("analytics_start_time", Date.now().toString());
      }
      return id;
    } catch (e) {
      if (!this.sessionIdFallback) {
        this.sessionIdFallback = Math.random().toString(36).substring(2) + Date.now().toString(36);
      }
      return this.sessionIdFallback;
    }
  }

  private getDeviceType(): DeviceType {
    const ua = navigator.userAgent;
    if (/Tablet|iPad/i.test(ua)) return DeviceType.TABLET;
    if (/Mobi|Android/i.test(ua)) return DeviceType.MOBILE;
    return DeviceType.DESKTOP;
  }

  private getOS(): string {
    const ua = navigator.userAgent;
    if (/Windows NT/i.test(ua)) return 'Windows';
    if (/Mac OS X/i.test(ua)) return 'macOS';
    if (/Android/i.test(ua)) return 'Android';
    if (/iPhone|iPad|iPod/i.test(ua)) return 'iOS';
    if (/Linux/i.test(ua)) return 'Linux';
    return 'Unknown';
  }

  trackClick(elementId: string, elementText?: string) {
    const e: ClickEvent = {
      event: "CLICK",
      ...this.getDefaultFields(),
      elementId,
      elementText
    };
    this.enqueue(e);
  }

  trackConversion(type: string, value?: number) {
    const e: ConversionEvent = {
      event: "CONVERSION",
      ...this.getDefaultFields(),
      type,
      value
    };
    this.enqueue(e);
  }

  trackError(errorType: string, message?: string) {
    const e: ErrorEvent = {
      event: "ERROR",
      ...this.getDefaultFields(),
      errorType,
      message
    };
    this.enqueue(e);
  }

  trackPageView(title: string) {
    const e: PageViewEvent = {
      event: "PAGE_VIEW",
      ...this.getDefaultFields(),
      title,
      screenWidth: window.innerWidth
    };
    this.enqueue(e);
  }

  trackScroll(depth: number) {
    const e: ScrollEvent = {
      event: "SCROLL",
      ...this.getDefaultFields(),
      depth
    };
    this.enqueue(e);
  }

  trackSearch(query: string, resultsCount?: number) {
    const e: SearchEvent = {
      event: "SEARCH",
      ...this.getDefaultFields(),
      query,
      resultsCount
    };
    this.enqueue(e);
  }

  private trackSessionStart() {
    const e: SessionStartEvent = {
      event: "SESSION_START",
      ...this.getDefaultFields()
    };
    this.enqueue(e);
  }

  private trackSessionEnd() {
    try {
      const startStr = sessionStorage.getItem("analytics_start_time");
      const start = startStr ? parseInt(startStr, 10) : Date.now();
      const duration = Date.now() - start;
      const e: SessionEndEvent = {
        event: "SESSION_END",
        ...this.getDefaultFields(),
        durationMs: duration
      };
      this.enqueue(e);
    } catch (e) {
      console.error("Failed to track session end", e);
    }
  }
}