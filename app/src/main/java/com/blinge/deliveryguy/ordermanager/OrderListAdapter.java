package com.blinge.deliveryguy.ordermanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.NameValueView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by rushabh on 19/03/16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderViewHolder> implements View.OnClickListener {

    ArrayList<OrderInformation> orderInformationArrayList;
    Context context;

    LayoutInflater inflater;


    public OrderListAdapter(ArrayList<OrderInformation> orderList, Context context) {
        this.context = context;
        orderInformationArrayList = orderList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_order_information, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);

        view.setOnClickListener(this);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        OrderInformation orderInformation = orderInformationArrayList.get(position);
        holder.orderInformation = orderInformation;
        holder.tvCustomerName.setValue(orderInformation.getCustomerName());
        holder.tvCustomerNumber.setValue(orderInformation.getCustomerNumber());
        holder.tvCustomerAddress.setValue(orderInformation.getCustomerAddress());
        holder.tvProductName.setValue(orderInformation.getProductName());
    }

    @Override
    public int getItemCount() {
        return orderInformationArrayList == null ? 0 : orderInformationArrayList.size();
    }

    @Override
    public void onClick(View v) {
        OrderViewHolder holder = (OrderViewHolder) v.getTag();

        OrderInformation orderInformation = holder.orderInformation;

        Intent intent = OrderDetailsActivity.getIntentToStartActivity(orderInformation, context);
        context.startActivity(intent);
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.nv_order_customer_name)
    NameValueView tvCustomerName;

    @Bind(R.id.nv_customer_number)
    NameValueView tvCustomerNumber;

    @Bind(R.id.nv_customer_address)
    NameValueView tvCustomerAddress;
    @Bind(R.id.nv_product_name)
    NameValueView tvProductName;

    OrderInformation orderInformation;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
