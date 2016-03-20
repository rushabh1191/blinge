package com.blinge.deliveryguy.ordermanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.BlingeRecycleView;
import com.blinge.deliveryguy.helpers.BlingeUtilities;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class ShowOrderListFragment extends Fragment {

    public static final String ARG_TYPE = "type";
    public static final String ARG_STATUS = "status";
    public static final String ARG_TITLE = "title";

    String type;
    String status;
    String title;

    @Bind(R.id.recycler_view)
    BlingeRecycleView recycleView;

    @Bind(R.id.tv_empty_view)
    TextView tvEmptyView;
    @Bind(R.id.pb_empty_view)
    ProgressBar pbEmptyView;

    OrderListAdapter adapter;

    ArrayList<OrderInformation> orderList = new ArrayList<>();


    public ShowOrderListFragment() {
    }

    public static ShowOrderListFragment getInstance(Bundle bundle) {
        ShowOrderListFragment fragment = new ShowOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ShowOrderListFragment getInstance(String type, String status, String title) {

        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        bundle.putString(ARG_STATUS, status);
        bundle.putString(ARG_TITLE, title);
        return getInstance(bundle);

    }


    void fetchOrderList(String type, String status) {

        ParseQuery<OrderInformation> query = ParseQuery.getQuery(OrderInformation.class);
        query.whereEqualTo("orderStatus", status);
        query.whereEqualTo("orderType", type);

        query.findInBackground(new FindCallback<OrderInformation>() {
            @Override
            public void done(List<OrderInformation> objects, ParseException e) {
                try {
                    pbEmptyView.setVisibility(View.GONE);
                    if (e == null) {
                        orderList.clear();
                        orderList.addAll(objects);
                        adapter.notifyDataSetChanged();

                        tvEmptyView.setText("Nothing to show");

                    }
                    else {
                        tvEmptyView.setText("Check your network connection");
                    }
                } catch (Exception ex) {

                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if(BlingeUtilities.isNetworkAvailable(getActivity())) {
            fetchOrderList(type, status);
        }
        else {
            BlingeUtilities.showNetworkNotAvailableDialog(getActivity());
            pbEmptyView.setVisibility(View.GONE);
            tvEmptyView.setText("No Internet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_order_list, container, false);

        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        type = bundle.getString(ARG_TYPE);
        status = bundle.getString(ARG_STATUS);
        title = bundle.getString(ARG_TITLE);

        getActivity().setTitle(title);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderListAdapter(orderList, getActivity());

        recycleView.setAdapter(adapter);

        recycleView.setEmptyView(view.findViewById(R.id.ll_empty_view));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
