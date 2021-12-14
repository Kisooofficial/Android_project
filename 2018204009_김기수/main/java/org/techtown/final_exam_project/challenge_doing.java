package org.techtown.final_exam_project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//챌린지가 선택된 경우에 따른 이벤트 처리 -> 추후 구현 예정
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
