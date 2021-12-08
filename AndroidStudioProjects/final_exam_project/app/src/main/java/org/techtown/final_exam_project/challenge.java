package org.techtown.final_exam_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class challenge extends AppCompatActivity {

    Button challenge_back, challenge_choose;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_operation);

        challenge_back = (Button) findViewById(R.id.challenge_back);
        challenge_choose = (Button) findViewById(R.id.choose_challenge);

        registerForContextMenu(challenge_choose);
        challenge_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(challenge.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.challenge, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.seven_day:challenge_choose.setText("seven days");return true;
            case R.id.fifteen_day:challenge_choose.setText("fifteen days"); return true;
            case R.id.thirty_day:challenge_choose.setText("thirty day");return true;
            case R.id.thirty_km:challenge_choose.setText("thirty km");return true;
            case R.id.fifty_km:challenge_choose.setText("fifty km"); return true;
            case R.id.hundred_km:challenge_choose.setText("hundred km");return true;
            default: return super.onContextItemSelected(item);
        }
    }
}
