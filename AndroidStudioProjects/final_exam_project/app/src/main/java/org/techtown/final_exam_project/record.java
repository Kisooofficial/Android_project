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

    Button searching_back;
    public static SQLiteDatabase mDB;
    String name, type, distance, average_speed;
    String[] names = {"a", "b", "c", "d"};
    ListView listView;
    ListItemAdapter adapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_record);
        searching_back = (Button) findViewById(R.id.searching_back);
        searching_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(record.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.ListData);

        adapter = new ListItemAdapter();

        //adapter.addItem(new ListItem("a", "b", "c", "d"));
        //adapter.addItem(new ListItem("b", "a", "d", "e"));

        //adapter.addItem(new ListItem("1", "2", "3", "4"));

        mDB = DB_helper.executeRawQuery();
        Cursor c1 = mDB.rawQuery("select * from running", null);

        String d = "날짜" + "\r\n" + "--" + "\r\n";
        String running_type = "구분" + "\r\n" + "--" + "\r\n";
        String dist = "거리" + "\r\n" + "--" + "\r\n";
        String av = "평균속력" + "\r\n" + "--" + "\r\n";

        while(c1.moveToNext()){
            d = "러닝/산책 번호 : " + c1.getString(0) + "번";
            running_type = "러닝/산책 종류 : " + c1.getString(1);
            dist = "거리 : " + c1.getString(3) + "km";
            av = "평균 속력 : " + c1.getString(4) + "km/h";
            adapter.addItem(new ListItem(running_type, d, dist, av));
        }
        listView.setAdapter(adapter);

    }







}

