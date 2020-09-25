package com.example.salonspace;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CustomReserveUserActivity extends LinearLayout {
    public CustomReserveUserActivity(Context context){
        super(context);
    }
    public CustomReserveUserActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomReserveUserActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomReserveUserActivity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.custom_reserve_user, this);
    }
}
