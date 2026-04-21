package com.blinge.deliveryguy.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.model.NoticeItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoticeCardPresenter extends Presenter {

    private static final SimpleDateFormat EMAIL_DATE_FORMAT =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private static final SimpleDateFormat DISPLAY_DATE_FORMAT =
            new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    static final class NoticeViewHolder extends ViewHolder {
        final TextView titleView;
        final TextView dateView;
        final TextView contentView;

        NoticeViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.notice_title);
            dateView = view.findViewById(R.id.notice_date);
            contentView = view.findViewById(R.id.notice_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notice, parent, false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        NoticeItem notice = (NoticeItem) item;
        NoticeViewHolder holder = (NoticeViewHolder) viewHolder;
        holder.titleView.setText(notice.getTitle());
        holder.contentView.setText(notice.getContent());
        holder.dateView.setText(formatDate(notice.getDate()));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        NoticeViewHolder holder = (NoticeViewHolder) viewHolder;
        holder.titleView.setText(null);
        holder.dateView.setText(null);
        holder.contentView.setText(null);
    }

    private String formatDate(String raw) {
        if (raw == null || raw.isEmpty()) return "";
        try {
            // Strip trailing timezone name in parentheses e.g. "(IST)"
            String cleaned = raw.replaceAll("\\s*\\(.*\\)\\s*$", "").trim();
            Date date = EMAIL_DATE_FORMAT.parse(cleaned);
            return DISPLAY_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            return raw;
        }
    }
}
