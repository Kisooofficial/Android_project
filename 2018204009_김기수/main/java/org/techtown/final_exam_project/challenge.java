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

//챌린지 도전 시작 화면
public class challenge extends AppCompatActivity {

    //버튼 생성
    Button challenge_back, challenge_choose;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_operation);

        //두 가지 버튼 생성(뒤로 가기, 챌린지 선택 버튼)
        challenge_back = (Button) findViewById(R.id.challenge_back);
        challenge_choose = (Button) findViewById(R.id.choose_challenge);

        registerForContextMenu(challenge_choose); // 컨텍스트 메뉴로 챌린지 선택
        
        //뒤로 가기
        challenge_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(challenge.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //챌린지 컨텍스트 메뉴
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();//inflate 이용하여 컨텍스트 메뉴 보여주기
        inflater.inflate(R.menu.challenge, menu);
    }

    //사용자가 컨텍스트 메뉴를 선택한 경우
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
