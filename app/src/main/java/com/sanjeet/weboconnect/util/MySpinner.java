package com.sanjeet.weboconnect.util;

import android.content.Context;
import android.util.AttributeSet;

public class MySpinner extends androidx.appcompat.widget.AppCompatSpinner {
    OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);

        if (position == getSelectedItemPosition()) {
            listener.onItemSelected(null, null, position, 0);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }
}
