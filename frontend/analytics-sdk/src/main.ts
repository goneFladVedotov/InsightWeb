import { Tracker } from './tracker';

const tracker = new Tracker({
    endpoint: 'http://localhost:8082/collect',
    bufferKey: 'demo_buffer',
    batchSize: 5,
    flushIntervalMs: 5000,
    disableTracking: false,
});


window.addEventListener('DOMContentLoaded', () => {
    tracker.trackPageView(document.title);
});

document.querySelectorAll('.nav-link').forEach(a => {
    a.addEventListener('click', () => {
        const section = (a as HTMLElement).dataset.nav || '';
        tracker.trackClick('nav-link', section);
        document.getElementById(section)?.scrollIntoView({ behavior: 'smooth' });
    });
});

const thresholds = [25, 50, 75, 100];
const hit = new Set<number>();
window.addEventListener('scroll', () => {
    const docHeight = document.documentElement.scrollHeight - window.innerHeight;
    const scrolled = (window.scrollY / docHeight) * 100;
    thresholds.forEach(t => {
        if (scrolled >= t && !hit.has(t)) {
            hit.add(t);
            tracker.trackScroll(t);
        }
    });
});

const searchForm = document.getElementById('search-form') as HTMLFormElement;
searchForm.addEventListener('submit', e => {
    e.preventDefault();
    const input = document.getElementById('search-input') as HTMLInputElement;
    const query = input.value.trim();
    if (!query) return;
    const resultsCount = Math.floor(Math.random() * 5) + 1;
    tracker.trackSearch(query, resultsCount);
});

const buyBtn = document.getElementById('buy-btn') as HTMLButtonElement;
buyBtn.addEventListener('click', () => {
    tracker.trackConversion('purchase', 99.99);
});

const subForm = document.getElementById('sub-form') as HTMLFormElement;
subForm.addEventListener('submit', e => {
    e.preventDefault();
    const email = (document.getElementById('email-input') as HTMLInputElement).value;
    if (!email.includes('@')) {
        tracker.trackError('invalid_email', 'Email не содержит @');
        return;
    }
    tracker.trackConversion('subscribe', 0);
});

const errBtn = document.getElementById('error-btn') as HTMLButtonElement;
errBtn.addEventListener('click', () => {
    try {
        throw new Error('Demo error');
    } catch (err: any) {
        tracker.trackError('demo_error', err.message);
    }
});