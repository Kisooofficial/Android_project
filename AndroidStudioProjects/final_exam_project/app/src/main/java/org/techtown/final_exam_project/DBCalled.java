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
  //  DBHelper helper;

    Button getData, bmain;
    EditText eDate, eType, eDistance, eAverage;
    public static SQLiteDatabase mDB;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        getData = (Button) findViewById(R.id.get_data);
        bmain = (Button) findViewById(R.id.backtomain);
        eDate = (EditText) findViewById(R.id.edtDate);
        eType = (EditText) findViewById(R.id.edtType);
        eDistance = (EditText) findViewById(R.id.edtDistance);
        eAverage = (EditText) findViewById(R.id.edtAverageSpeed);

        bmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DBCalled.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDB = DB_helper.executeRawQuery();
                Cursor c1 = mDB.rawQuery("select * from running", null);

                String d = "날짜" + "\r\n" + "--" + "\r\n";
                String running_type = "구분" + "\r\n" + "--" + "\r\n";
                String dist = "거리" + "\r\n" + "--" + "\r\n";
                String av = "평균속력" + "\r\n" + "--" + "\r\n";

                while(c1.moveToNext()){
                    d += c1.getString(0) + "\r\n";
                    running_type += c1.getString(1) + "\r\n";
                    dist += c1.getString(3) + "\r\n";
                    av += c1.getString(4) + "\r\n";
                }

                eDate.setText(d);
                eType.setText(running_type);
                eDistance.setText(dist);
                eAverage.setText(av);
                c1.close();
            }
        });

/*

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM running", null);

        startManagingCursor(cursor);

        String[] from = {"running", "date"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, from, to);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);*/
    }
}
