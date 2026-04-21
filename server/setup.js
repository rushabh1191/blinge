/**
 * One-time OAuth2 setup. Run once with: node setup.js
 *
 * Steps before running:
 *   1. Go to https://console.cloud.google.com
 *   2. Create a project → Enable Gmail API
 *   3. OAuth consent screen → External → add your Gmail as test user
 *   4. Credentials → Create OAuth client ID → Desktop app
 *   5. Copy Client ID and Client Secret into .env
 *   6. Run: node setup.js
 *   7. Open the printed URL, authorize, and the refresh token saves automatically
 */

require('dotenv').config();
const { google } = require('googleapis');
const http = require('http');
const url = require('url');
const fs = require('fs');

const { CLIENT_ID, CLIENT_SECRET } = process.env;
if (!CLIENT_ID || !CLIENT_SECRET) {
    console.error('Set CLIENT_ID and CLIENT_SECRET in .env first');
    process.exit(1);
}

const REDIRECT_URI = 'http://localhost:8080/callback';
const oauth2Client = new google.auth.OAuth2(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

const authUrl = oauth2Client.generateAuthUrl({
    access_type: 'offline',
    scope: ['https://www.googleapis.com/auth/gmail.readonly'],
    prompt: 'consent',
});

console.log('\nOpen this URL in your browser:\n');
console.log(authUrl);
console.log('\nWaiting for authorization...\n');

const server = http.createServer(async (req, res) => {
    const { query } = url.parse(req.url, true);
    if (!query.code) return;

    res.end('<h2>Authorization successful! You can close this window.</h2>');
    server.close();

    try {
        const { tokens } = await oauth2Client.getToken(query.code);
        let env = fs.existsSync('.env') ? fs.readFileSync('.env', 'utf8') : '';
        if (env.includes('REFRESH_TOKEN=')) {
            env = env.replace(/REFRESH_TOKEN=.*/g, `REFRESH_TOKEN=${tokens.refresh_token}`);
        } else {
            env += `\nREFRESH_TOKEN=${tokens.refresh_token}`;
        }
        fs.writeFileSync('.env', env);
        console.log('Refresh token saved to .env');
        console.log('You can now run: npm start');
    } catch (err) {
        console.error('Failed to get token:', err.message);
    }
});

server.listen(8080);
