package org.techtown.final_exam_project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class challenge_doing extends LinearLayout {

    public challenge_doing(Context context) {
        super(context);
    }

    public challenge_doing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public challenge_doing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public challenge_doing(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.challenge, this);
        addView(v);
    }

}
