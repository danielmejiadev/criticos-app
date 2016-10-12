package com.android.criticos.app;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

/**
 * Created by Daniel on 04/10/2016.
 */
public class DelayAutoCompleteTextView extends AutoCompleteTextView {

    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 600;

    private int timeDelay = DEFAULT_AUTOCOMPLETE_DELAY;

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    public DelayAutoCompleteTextView(Context context,AttributeSet attributeSet)
    {
        super(context,attributeSet);


    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode)
    {

        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), timeDelay);
    }

    @Override
    public void onFilterComplete(int count)
    {

        super.onFilterComplete(count);
    }

    public void setTimeDelay(int autoCompleteDelay)
    {
        this.timeDelay = autoCompleteDelay;
    }

}
