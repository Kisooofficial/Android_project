package org.techtown.final_exam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//MainActivity에서 기록 측정하기 버튼 후 나오는 화면
//이 화면을 거치고 기록이 측정되기 시작
public class radio_button extends AppCompatActivity {
    public static String running_type = "run"; // 산책과 러닝 타입 디폴트를 러닝으로 지정

    //버튼 두가지 생성(시작, 뒤로가기)
    Button StartButton;
    Button cancelButton;
    RadioButton run;
    RadioButton walk;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_measuring_button);

        StartButton = (Button) findViewById(R.id.start);
        cancelButton = (Button) findViewById(R.id.cancel);
        run = (RadioButton) findViewById(R.id.running);
        walk = (RadioButton) findViewById(R.id.walking);
        
        //버튼 클릭에 대한 인텐트 객체 생성
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//버튼을 누르지 않은 경우
                if(run.isChecked() == false && walk.isChecked() == false){
                    Toast.makeText(getApplicationContext(), "둘 중 하나를 선택하세요", Toast.LENGTH_SHORT).show();
                }
                else {//버튼을 누르면 시작
                    Intent intent = new Intent(radio_button.this, measuring.class);
                    startActivity(intent);
                }
            }
        });

        //취소버튼 클릭
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(radio_button.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
    //라디오 버튼 클릭 여부에 따라 러닝 타입 지정
    public void clicked(View v){
        if(v.getId() == R.id.walking) running_type = "walk";
    }

}
