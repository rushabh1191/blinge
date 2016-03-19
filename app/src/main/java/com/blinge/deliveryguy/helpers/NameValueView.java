package com.blinge.deliveryguy.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blinge.deliveryguy.R;


/**
 * Created by rushabh on 05/02/16.
 */
public class NameValueView extends LinearLayout {

    TextView tvValue;
    TextView tvName;


    public NameValueView(Context context) {
        super(context);
    }

    public NameValueView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view=View.inflate(context, R.layout.view_name_value,this);

        tvValue= (TextView) view.findViewById(R.id.tv_value);
        tvName= (TextView) view.findViewById(R.id.tv_name);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.NameValueView,0,0);
        try {

            String name=array.getText(R.styleable.NameValueView_name).toString();
            String value=array.getText(R.styleable.NameValueView_value).toString();

            tvValue.setText(value);
            tvName.setText(name);
        }
        finally {
            array.recycle();
        }
    }

    public NameValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView getValueText(){
        return  tvValue;
    }
    public void setValue(String value){
        tvValue.setText(value);
    }

    public void setLabel(String label){
        tvName.setText(label);
    }

}
