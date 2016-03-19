package com.blinge.deliveryguy.helpers;

/**
 * Created by rushabh on 20/02/16.
 */

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class BlingeRecycleView extends RecyclerView {

    LinearLayoutManager mLayoutManager;
    private View emptyView;
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {


        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    BlingeRecycleView.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    BlingeRecycleView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public BlingeRecycleView(Context context) {
        super(context);

    }

    public BlingeRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public BlingeRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setAnimator(Context context) {
        this.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        this.setLayoutManager(mLayoutManager);
        this.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();

    }
}

