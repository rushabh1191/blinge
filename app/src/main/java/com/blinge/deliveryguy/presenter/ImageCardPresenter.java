package com.blinge.deliveryguy.presenter;

import android.view.ViewGroup;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import com.bumptech.glide.Glide;
import com.blinge.deliveryguy.model.ImageItem;

public class ImageCardPresenter extends Presenter {

    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 225;

    static final class ImageViewHolder extends ViewHolder {
        final ImageCardView cardView;

        ImageViewHolder(ImageCardView view) {
            super(view);
            cardView = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageCardView cardView = new ImageCardView(parent.getContext());
        cardView.setCardType(ImageCardView.CARD_TYPE_MAIN_ONLY);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        return new ImageViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ImageItem imageItem = (ImageItem) item;
        ImageViewHolder holder = (ImageViewHolder) viewHolder;
        Glide.with(holder.view.getContext())
                .load(imageItem.getImageUrl())
                .centerCrop()
                .into(holder.cardView.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageViewHolder holder = (ImageViewHolder) viewHolder;
        Glide.with(holder.view.getContext()).clear(holder.cardView.getMainImageView());
        holder.cardView.setMainImage(null);
    }
}
