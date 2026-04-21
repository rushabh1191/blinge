require('dotenv').config();
const { google } = require('googleapis');

let cache = { notices: null, fetchedAt: 0 };
const CACHE_TTL_MS = 5 * 60 * 1000; // 5 minutes

function getAuthClient() {
    const oauth2Client = new google.auth.OAuth2(
        process.env.CLIENT_ID,
        process.env.CLIENT_SECRET,
        'http://localhost:8080/callback'
    );
    oauth2Client.setCredentials({ refresh_token: process.env.REFRESH_TOKEN });
    return oauth2Client;
}

function decodeBase64Url(str) {
    return Buffer.from(str.replace(/-/g, '+').replace(/_/g, '/'), 'base64').toString('utf-8');
}

function extractPlainText(payload) {
    if (payload.mimeType === 'text/plain' && payload.body?.data) {
        return decodeBase64Url(payload.body.data);
    }
    if (payload.parts) {
        for (const part of payload.parts) {
            const text = extractPlainText(part);
            if (text) return text;
        }
        // Fall back to HTML stripped of tags
        for (const part of payload.parts) {
            if (part.mimeType === 'text/html' && part.body?.data) {
                return decodeBase64Url(part.body.data)
                    .replace(/<style[^>]*>[\s\S]*?<\/style>/gi, '')
                    .replace(/<[^>]+>/g, ' ')
                    .replace(/\s{2,}/g, ' ')
                    .trim();
            }
        }
    }
    return '';
}

function getHeader(headers, name) {
    return headers.find(h => h.name.toLowerCase() === name.toLowerCase())?.value || '';
}

async function fetchNotices() {
    if (cache.notices && Date.now() - cache.fetchedAt < CACHE_TTL_MS) {
        return cache.notices;
    }

    const auth = getAuthClient();
    const gmail = google.gmail({ version: 'v1', auth });
    const query = process.env.GMAIL_QUERY || 'from:nobrokerhood.com';

    const listRes = await gmail.users.messages.list({
        userId: 'me',
        q: query,
        maxResults: 20,
    });

    const messages = listRes.data.messages || [];

    const notices = await Promise.all(
        messages.map(async (msg) => {
            const detail = await gmail.users.messages.get({
                userId: 'me',
                id: msg.id,
                format: 'full',
            });
            const headers = detail.data.payload.headers;
            const body = extractPlainText(detail.data.payload);
            return {
                id: msg.id,
                title: getHeader(headers, 'Subject') || 'Notice',
                from: getHeader(headers, 'From'),
                date: getHeader(headers, 'Date'),
                content: body.substring(0, 600).trim(),
            };
        })
    );

    cache = { notices, fetchedAt: Date.now() };
    return notices;
}

module.exports = { fetchNotices };
