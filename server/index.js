require('dotenv').config();
const express = require('express');
const cors = require('cors');
const { fetchNotices } = require('./gmail');

const app = express();
app.use(cors());

app.get('/api/notices', async (req, res) => {
    try {
        const notices = await fetchNotices();
        res.json(notices);
    } catch (err) {
        console.error('Failed to fetch notices:', err.message);
        res.status(500).json({ error: 'Failed to fetch notices' });
    }
});

app.get('/health', (req, res) => res.json({ status: 'ok' }));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Blinge TV server running on port ${PORT}`));
