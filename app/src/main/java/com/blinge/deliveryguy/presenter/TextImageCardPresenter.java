package com.blinge.deliveryguy.presenter;

import android.view.ViewGroup;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import com.bumptech.glide.Glide;
import com.blinge.deliveryguy.model.TextImageItem;

public class TextImageCardPresenter extends Presenter {

    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 225;

    static final class TextImageViewHolder extends ViewHolder {
        final ImageCardView cardView;

        TextImageViewHolder(ImageCardView view) {
            super(view);
            cardView = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageCardView cardView = new ImageCardView(parent.getContext());
        cardView.setCardType(ImageCardView.CARD_TYPE_INFO_UNDER);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        return new TextImageViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextImageItem textImageItem = (TextImageItem) item;
        TextImageViewHolder holder = (TextImageViewHolder) viewHolder;
        holder.cardView.setTitleText(textImageItem.getTitle());
        holder.cardView.setContentText(textImageItem.getDescription());
        Glide.with(holder.view.getContext())
                .load(textImageItem.getImageUrl())
                .centerCrop()
                .into(holder.cardView.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        TextImageViewHolder holder = (TextImageViewHolder) viewHolder;
        Glide.with(holder.view.getContext()).clear(holder.cardView.getMainImageView());
        holder.cardView.setMainImage(null);
    }
}
