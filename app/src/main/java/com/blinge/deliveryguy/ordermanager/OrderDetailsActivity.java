package com.blinge.deliveryguy.ordermanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.TextView;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.helpers.BlingeUtilities;
import com.blinge.deliveryguy.helpers.ConfirmationWindow;
import com.blinge.deliveryguy.helpers.NameValueView;
import com.blinge.deliveryguy.helpers.ParseProxyObject;
import com.parse.ParseException;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BlingeBaseActivity {

    OrderInformation orderInformation;

    static String ARG_ORDER_INFO = "order_info";

    @Bind(R.id.nv_order_customer_name)
    NameValueView nvCustomerName;
    @Bind(R.id.tv_customer_number)
    TextView tvCustomerNumber;
    @Bind(R.id.nv_customer_address)
    NameValueView nvCustomerAddress;
    @Bind(R.id.nv_product_name)
    NameValueView nvProductName;

    @Bind(R.id.btn_update_status)
    Button btnDelivered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        enableBackpress();
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        ParseProxyObject parseProxyObject =
                (ParseProxyObject) bundle.getSerializable(ARG_ORDER_INFO);
        orderInformation = new OrderInformation(parseProxyObject);

        if (orderInformation.getOrderType().equals(OrderInformation.TYPE_PICKUP)) {
            btnDelivered.setText("Product Picked");
        }

        if (orderInformation.getOrderStatus().equals(OrderInformation.STATUS_COMPLETED)) {
            btnDelivered.setEnabled(false);
            String text;


            if (orderInformation.getOrderType().equals(OrderInformation.TYPE_PICKUP)) {

                text = "Order Picked Up From Customer";
            } else {

                text = "Order Already delivered to customer";
            }
            btnDelivered.setText(text);
        }
        nvCustomerName.setValue(orderInformation.getCustomerName());
        tvCustomerNumber.setText(orderInformation.getCustomerNumber());
        nvCustomerAddress.setValue(orderInformation.getCustomerAddress());
        nvProductName.setValue(orderInformation.getProductName());
    }

    @OnClick(R.id.btn_update_status)
    void updateStatus() {




        orderInformation.setOrderStatus(OrderInformation.STATUS_COMPLETED);

        if (BlingeUtilities.isNetworkAvailable(this)) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Updating the status");
            orderInformation.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    progressDialog.dismiss();
                    if (e == null) {
                        Snackbar snackbar = Snackbar.make(tvCustomerNumber, "Status Updated", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        snackbar.setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                finish();
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }
                        });
                    } else {
                        new ConfirmationWindow(OrderDetailsActivity.this, "Error",
                                "Please check your network connection", "OK", "");
                    }
                }
            });
        } else {
            BlingeUtilities.showNetworkNotAvailableDialog(this);
        }
    }

    @OnClick(R.id.tv_customer_number)
    void callUser() {
        BlingeUtilities.call(orderInformation.getCustomerNumber(), this);
    }

    public static Intent getIntentToStartActivity(OrderInformation orderInformation, Context context) {
        ParseProxyObject parseProxyObject = new ParseProxyObject(orderInformation);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ORDER_INFO, parseProxyObject);
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(ARG_ORDER_INFO, parseProxyObject);
        return intent;

    }
}
