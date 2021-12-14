package org.techtown.final_exam_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class record extends AppCompatActivity {

    Button searching_back; // 뒤로가기
    public static SQLiteDatabase mDB; //데이터베이스 접근
    String name, type, distance, average_speed; // 데이터 레코드
    ListView listView; //리스트뷰
    ListItemAdapter adapter;// 리스트뷰에 있던 거를 보여주기 위한 어댑터
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_record);
        searching_back = (Button) findViewById(R.id.searching_back); //뒤로 가기 버튼
        //뒤로 가기 버튼에 대한 이벤트 처리
        searching_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(record.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.ListData); // 리스트뷰 가져오기 위해 findViewById 사용

        adapter = new ListItemAdapter();//리스트뷰를 현재 뷰에 보여주기 위해 어댑터 선언
        mDB = DB_helper.executeRawQuery();//데이터베이스 조회하기
        Cursor c1 = mDB.rawQuery("select * from running", null);
        
        while(c1.moveToNext()){//다음 테이터가 넘어갈 때까지 Cursor 이용
            name = "러닝/산책 번호 : " + c1.getString(0) + "번";
            type = "러닝/산책 종류 : " + c1.getString(1);
            distance = "거리 : " + c1.getString(3) + "km";
            average_speed = "평균 속력 : " + c1.getString(4) + "km/h";
            adapter.addItem(new ListItem(type, name, distance, average_speed));
        }
        listView.setAdapter(adapter);

    }







}

