package com.blinge.deliveryguy.ordermanager;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rushabh on 19/03/16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    ArrayList<OrderInformation> orderInformationArrayList;
    Context context;

    LayoutInflater inflater;

    public OrderListAdapter(ArrayList<OrderInformation> orderInformations,Context context){
        this.context=context;
        orderInformationArrayList=orderInformations;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.item_order_information,parent,false);
        OrderViewHolder holder=new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        OrderInformation orderInformation=orderInformationArrayList.get(position);

        holder.textView.setText(orderInformation.getCustomerName());

    }

    @Override
    public int getItemCount() {
        return orderInformationArrayList==null?0:orderInformationArrayList.size();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.tv_order_name)
    TextView textView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
