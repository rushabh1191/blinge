package com.blinge.deliveryguy.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.model.TextItem;

public class TextCardPresenter extends Presenter {

    static final class TextViewHolder extends ViewHolder {
        final TextView titleView;
        final TextView contentView;

        TextViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.text_title);
            contentView = view.findViewById(R.id.text_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_text, parent, false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextItem textItem = (TextItem) item;
        TextViewHolder holder = (TextViewHolder) viewHolder;
        holder.titleView.setText(textItem.getTitle());
        holder.contentView.setText(textItem.getContent());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        TextViewHolder holder = (TextViewHolder) viewHolder;
        holder.titleView.setText(null);
        holder.contentView.setText(null);
    }
}
