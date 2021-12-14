package org.techtown.final_exam_project;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.security.KeyStore;

//커스텀 뷰 (이름이 메뉴바, 실제 메뉴바가 아님)
public class menubar extends LinearLayout {
    //커스텀 뷰를 불러오는 데 필요한 레이아웃 선언
    private Context context;
    LinearLayout bg;
    Button measure, challenge, search, record, stats, controller, controller2;

    public menubar(Context context){//생성자
        super(context);
        this.context = context;
        initView();
    }

    public menubar(Context context, AttributeSet attrs){//생성자
        super(context, attrs);
        this.context = context;
        initView();
    }

    public menubar(Context context, AttributeSet attrs, int defStyle){//생성자
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }
    public menubar(Context context, AttributeSet attrs, int defStyle, int defStyleRes){//생성자 
        super(context, attrs, defStyle, defStyleRes);
        this.context = context;
        initView();
    }

    private void initView(){//커스텀 뷰 실행시
        //xml에 있는 모든 뷰 가져오기
        bg = (LinearLayout) findViewById(R.id.main);
        measure = (Button) findViewById(R.id.measure);
        challenge = (Button) findViewById(R.id.challenge);
        search = (Button) findViewById(R.id.search);
        stats = (Button) findViewById(R.id.stats);
        controller = (Button) findViewById(R.id.music_control);
        record = (Button) findViewById(R.id.total_record);
        controller2 = (Button)findViewById(R.id.music_control2);

        //inflate 해서 커스텀 뷰 보여주기
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.menubar,this, false);
        addView(v);


    }




}
