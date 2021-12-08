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

public class statistics extends AppCompatActivity {

    LinearLayout baseLayout;
    Button stats_back, check_progress;
    TextView progress_View, progress_text, distance_goal;
    Button goal_View;
    ProgressBar p;
    float total = 0;
    public static SQLiteDatabase mDB;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystats);

        stats_back = (Button) findViewById(R.id.stats_back);
        check_progress = (Button) findViewById(R.id.check_progress);
        goal_View = (Button) findViewById(R.id.goal);
        progress_View = (TextView) findViewById(R.id.distance_record);
        progress_text = (TextView) findViewById(R.id.progress_text);
        distance_goal = (TextView) findViewById(R.id.goal_distance);

        baseLayout = findViewById(R.id.record);
        p = (ProgressBar) findViewById(R.id.progressBar2);
        p.setProgress(0);

        stats_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(statistics.this, MainActivity.class);
                startActivity(intent);
            }
        });



        /*check_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int final_goal = Integer.parseInt(distance_goal.getText().toString());
                    p.setProgress(Integer.parseInt(progress_View.getText().toString()) * 100 / final_goal);
                    progress_text.setText(Integer.parseInt(progress_View.getText().toString()) * 100 / final_goal + "%");
                }catch(ArithmeticException e){
                    Toast.makeText(getApplicationContext(), "정수형이 아닙니다.", Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }

    public void progress_click(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("확인하시겠습니까?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int final_goal = Integer.parseInt(distance_goal.getText().toString());
                    p.setProgress(Integer.parseInt(progress_View.getText().toString()) * 100 / final_goal);
                    progress_text.setText(Integer.parseInt(progress_View.getText().toString()) * 100 / final_goal + "%");
                } catch (ArithmeticException e) {
                    Toast.makeText(getApplicationContext(), "정수형이 아닙니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    public void popup_click(View v){
        mDB = DB_helper.executeRawQuery();
        Cursor c1 = mDB.rawQuery("select * from running", null);
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.goal, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                distance_goal.setText(item.getTitle());
                return true;
            }
        });
        popup.show();

        while(c1.moveToNext()){
            total += Float.parseFloat(c1.getString(3));
        }

        progress_View.setText(String.valueOf(total));
    }


}
