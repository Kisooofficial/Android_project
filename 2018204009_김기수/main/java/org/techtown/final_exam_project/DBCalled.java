package org.techtown.final_exam_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DBCalled extends AppCompatActivity {

    Button getData, bmain; // 데이터를 가져오는 버튼과 다시 메인으로 돌아가는 버튼
    EditText eDate, eType, eDistance, eAverage; // 데이터베이스에 저장되는 값들의 모음
    public static SQLiteDatabase mDB; // DB를 사용하기 위한 객체
    int count = 0; //데이터베이스에 보여줄 데이터의 개수 저장
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        //xml에서 설정했던 뷰들을 가져오도록 findViewById를 이용
        getData = (Button) findViewById(R.id.get_data);
        bmain = (Button) findViewById(R.id.backtomain);
        eDate = (EditText) findViewById(R.id.edtDate);
        eType = (EditText) findViewById(R.id.edtType);
        eDistance = (EditText) findViewById(R.id.edtDistance);
        eAverage = (EditText) findViewById(R.id.edtAverageSpeed);

        //버튼과 에디트텍스트에 대한 이벤트 처리
        bmain.setOnClickListener(new View.OnClickListener() { // 다시 메인으로 돌아가는 버튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DBCalled.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getData.setOnClickListener(new View.OnClickListener() { // 데이터를 불러오는 버튼
            @Override
            public void onClick(View v) {
                mDB = DB_helper.executeRawQuery(); // 데이터베이스에 저장된 커리를 모두 가져온다.
                Cursor c1 = mDB.rawQuery("select * from running order by AVERAGE_SPEED DESC", null); // 데이터 가져오기 위해 Cursor 사용

                // 데이터를 불러오기 위해 각 데이터 값에 해당하는 문자열 저장
                String num = "번호" + "\r\n" + "--" + "\r\n";
                String running_type = "구분" + "\r\n" + "--" + "\r\n";
                String distance = "거리" + "\r\n" + "--" + "\r\n";
                String average_speed = "평균속력" + "\r\n" + "--" + "\r\n";

                while(c1.moveToNext() & count < 8){//다음 데이터가 있는 경우, 상위 8개 출력
                    num += c1.getString(0) + "\r\n";
                    running_type += c1.getString(1) + "\r\n";
                    distance += c1.getString(3) + "\r\n";
                    average_speed += c1.getString(4) + "\r\n";
                    count++;
                }

                //각 변수에 저장된 값들을 해당 레이아웃에 표시
                eDate.setText(num);
                eType.setText(running_type);
                eDistance.setText(distance);
                eAverage.setText(average_speed);
                c1.close();
            }
        });


    }
}
