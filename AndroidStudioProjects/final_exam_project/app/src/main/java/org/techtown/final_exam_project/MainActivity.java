package org.techtown.final_exam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private DB_helper db_helper;
    SQLiteDatabase db;
    Button music_on, music_off, total_record;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        music_on = (Button) findViewById(R.id.music_control);
        music_off = (Button) findViewById(R.id.music_control2);
        total_record = (Button) findViewById(R.id.total_record);
        db_helper = new DB_helper(this);
        db_helper.open();
        db_helper.create();

        music_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            }
        });

        music_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

    }

    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.measure:
            {intent = new Intent(MainActivity.this, radio_button.class);
                startActivity(intent);
            }break;
            case R.id.challenge:
            {intent = new Intent(MainActivity.this, challenge.class);
                startActivity(intent);} break;
            case R.id.search: {intent = new Intent(MainActivity.this, DBCalled.class);
                startActivity(intent);}break;
            case R.id.stats: {intent = new Intent(MainActivity.this, statistics.class);
                startActivity(intent);}break;
            case R.id.total_record:{intent = new Intent(MainActivity.this, record.class);
            startActivity(intent);}break;
            default: break;
        }

    }


    
}
