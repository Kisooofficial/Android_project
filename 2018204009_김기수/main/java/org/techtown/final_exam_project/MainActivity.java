package org.techtown.final_exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private DB_helper db_helper; // 데이터베이스 선언
    SQLiteDatabase db; // 데이터베이스 생성/삭제 도와주는 객체 생성
    Button music_on, music_off, total_record, start; //백그라운드에서 음악 실행 버튼, 기록 조회 버튼

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menubar customView = new menubar(this);//커스텀 뷰 불러오기
        setContentView(customView);


        //activity_main에서 사용했던 뷰들을 모두 가져오기
        music_on = (Button) findViewById(R.id.music_control);
        music_off = (Button) findViewById(R.id.music_control2);
        total_record = (Button) findViewById(R.id.total_record);
        db_helper = new DB_helper(this);
        db_helper.open();
        db_helper.create();

        //음악 켜기 버튼에 대한 이벤트 처리
        music_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            }
        });

        //음악 끄기 버튼에 대한 이벤트 처리
        music_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

    }

    // 나머지 버튼에 대해서는 인텐트 객체 실행해야 하므로 onClick 속성 추가
    public void onClick(View v){
        Intent intent;//인텐트 객체 선언
        switch(v.getId()){
            case R.id.measure://기록 측정
            {intent = new Intent(MainActivity.this, radio_button.class);
                startActivity(intent);
            }break;
            case R.id.challenge://챌린지 도전
            {intent = new Intent(MainActivity.this, challenge.class);
                startActivity(intent);} break;
            case R.id.search: //기록 조회
                {intent = new Intent(MainActivity.this, DBCalled.class);
                startActivity(intent);}break;
            case R.id.stats: //나의 상태 확인
                {intent = new Intent(MainActivity.this, statistics.class);
                startActivity(intent);}break;
            case R.id.total_record: //전체 기록 리스트 조회
                {intent = new Intent(MainActivity.this, record.class);
            startActivity(intent);}break;
            default: break;
        }

    }


    
}
