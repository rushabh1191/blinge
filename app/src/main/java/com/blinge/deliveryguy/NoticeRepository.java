package com.blinge.deliveryguy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.blinge.deliveryguy.model.NoticeItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NoticeRepository {

    private static final Gson gson = new Gson();

    public static List<NoticeItem> fetchNotices(String apiUrl) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("GET");

        int code = conn.getResponseCode();
        if (code != 200) return new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        Type listType = new TypeToken<List<NoticeItem>>() {}.getType();
        List<NoticeItem> result = gson.fromJson(sb.toString(), listType);
        return result != null ? result : new ArrayList<>();
    }
}
