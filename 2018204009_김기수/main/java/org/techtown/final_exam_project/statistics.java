package org.techtown.final_exam_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOError;
import java.io.IOException;


//기록 조회
public class statistics extends AppCompatActivity {

    //레이아웃 가져오기
    LinearLayout baseLayout;
    Button stats_back, check_progress;
    TextView progress_View, progress_text, distance_goal;
    Button goal_View;
    ProgressBar p;
    float total = 0.0f;
    public static SQLiteDatabase mDB;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystats);

        //레이아웃 연결(findviewbyid)
        stats_back = (Button) findViewById(R.id.stats_back);
        check_progress = (Button) findViewById(R.id.check_progress);
        goal_View = (Button) findViewById(R.id.goal);
        progress_View = (TextView) findViewById(R.id.distance_record);
        progress_text = (TextView) findViewById(R.id.progress_text);
        distance_goal = (TextView) findViewById(R.id.goal_distance);

        baseLayout = findViewById(R.id.record);
        p = (ProgressBar) findViewById(R.id.progressBar2);
        p.setProgress(0);

        //이벤트 처리(인텐트)
        stats_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(statistics.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //데이터베이스 가져오기
        mDB = DB_helper.executeRawQuery();
        Cursor c1 = mDB.rawQuery("select * from running", null);

        while(c1.moveToNext()){//기록된 모든 거리의 합을 저장
            total += Float.parseFloat(c1.getString(3));
        }




    }

    public void progress_click(View v) {//대화상자
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("확인하시겠습니까?");//질문
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {//yes를 누르면
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //목표 진행상황을 보여주는 프로그레스바 및 거리
                    int final_goal = Integer.parseInt(distance_goal.getText().toString());
                    int percent_complete = (int) (total * 100 / final_goal);
                    progress_View.setText(String.valueOf(total));
                    p.setProgress(percent_complete);
                    progress_text.setText(String.valueOf(percent_complete) + "%");
                }catch(NumberFormatException a){
                    Toast.makeText(getApplicationContext(), "목표를 먼저 설정하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//no를 누르면

                Toast.makeText(getApplicationContext(), "다시 선택하세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //팝업상자 보여주기
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    public void popup_click(View v){//팝업상자 클릭 시

        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.goal, popup.getMenu());//팝업상자에 해당하는 메뉴 가져오기
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                distance_goal.setText(item.getTitle());
                return true;
            }
        });
        popup.show();

    }


}
