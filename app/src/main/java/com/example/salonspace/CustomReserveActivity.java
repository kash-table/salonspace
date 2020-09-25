package com.example.salonspace;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CustomReserveActivity extends LinearLayout {
    public CustomReserveActivity(Context context){
        super(context);
    }
    public CustomReserveActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomReserveActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomReserveActivity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.custom_reserve, this);
    }
}
