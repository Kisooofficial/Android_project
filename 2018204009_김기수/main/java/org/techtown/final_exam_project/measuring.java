package org.techtown.final_exam_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class measuring extends AppCompatActivity {
    private TextView gx, gy, gz; // 센서값 측정 데이터
    private TextView stepsTextView, sensitiveTextView; // 만보기 관련 변수(걸음, 센서값)
    private TextView distanceView, averageSpeedView; // 만보기 기록 토대로 거리와 평균 속력 계산
    private Chronometer timeView; // 시간 계산
    private Button reset, start, save, back, check;// 기록 측정 관련 버튼
    private SeekBar seekBar;
    
    private SensorManager sensorManager;//센서 감지 
    private float acceleration; //가속도 
    private DB_helper db_helper; //데이터베이스 생성/기록한 거 저장

    private Date today = new Date(); //시간 계산위해 Date 객체 생성
    private float previousY, currentY;//센서 y축 감지
    private int steps;// 만보기 걸음 횟수

    private float distance;//뛴(걸은) 거리
    private float time;//뛴(걸은) 시간

    private double average_speed;//평균 속력
    private long start_time = 0;//시작 시간 저장
    private long end_time;//끝나는 시간 저장
    private int threshold;//임계치(센서 감지 바)
    private long stopTime = 0;//끝나는 시간
    private int time_vision = 0;//시작 버튼 누르면 1, 아닌 경우 0 -> 시작버튼을 누르지 않은 경우 만보기 걸음 수 올라가지 않도록 선언한 것
    String running_type = radio_button.running_type;// 산책/ 러닝 타입

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_measure);

        db_helper = new DB_helper(this); //데이터베이스 불러오기
        db_helper.open();
        db_helper.create();

        //menubar.xml에 있는 모든 요소들을 findViewById를 통해 뷰로 가져온다.
        gx = (TextView) findViewById(R.id.gx);
        gy = (TextView) findViewById(R.id.gy);
        gz = (TextView) findViewById(R.id.gz);

        stepsTextView = (TextView) findViewById(R.id.steps);
        sensitiveTextView = (TextView) findViewById(R.id.sensitive);
        distanceView = (TextView) findViewById(R.id.distance_number);

        averageSpeedView = (TextView) findViewById(R.id.average_speed_number);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        reset = (Button) findViewById(R.id.reset);
        start = (Button) findViewById(R.id.StartButton);
        save = (Button) findViewById(R.id.SaveButton);
        back = (Button) findViewById(R.id.back);
        check = (Button) findViewById(R.id.checking_data);

        threshold = 5; //센서값의 디폴트 값을 5로 설정
        seekBar.setProgress(threshold); // 시크바의 기본 default 값을 5로 설정
        seekBar.setOnSeekBarChangeListener(seekBarListener);//시크바의 센서값을 바꾸는 이벤트가 발생할 경우 호출
        sensitiveTextView.setText(String.valueOf(threshold));
        timeView = (Chronometer) findViewById(R.id.time_number);


        previousY = currentY = steps = 0;//러닝 시작 전 0으로 초기화

        acceleration = 0.0f;//가속도 0으로 설정

        //센서 감지 매니저 선언
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        
        //각 버튼에 대한 이벤트 처리
        back.setOnClickListener(new View.OnClickListener(){//뒤로 가기
            public void onClick(View v){
                Intent intent = new Intent(measuring.this, MainActivity.class);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {//러닝 시작

            @Override
            public void onClick(View v) {
                time_vision = 1;//러닝 시작/종료 구분하는 time_vision 변경
                start_time = SystemClock.elapsedRealtime();//현재시간
                timeView.setBase(SystemClock.elapsedRealtime() + stopTime);//경과된 시간
                timeView.start();
                start.setVisibility(View.GONE);//start 버튼 숨기기
            }
        });

        //저장버튼
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                db_helper.insertColum(today, running_type, distance, average_speed);//데이터베이스에 삽입, 저장
                //러닝과 관련된 모든 기록 초기화
                time_vision = steps = 0;
                distance = 0;
                average_speed = 0;

                //러닝 기록들을 화면에 보여주기
                stepsTextView.setText(String.valueOf(steps));
                distanceView.setText(String.valueOf(distance));
                averageSpeedView.setText(String.valueOf(average_speed));
                timeView.setBase(SystemClock.elapsedRealtime());
                //시간은 다시 0으로
                stopTime = 0;
                timeView.stop();
                start.setVisibility(View.VISIBLE);//시작버튼 보이기

            }
        });


        //버튼 클릭이 잘 되는 지 테스트
        check.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "체크함", Toast.LENGTH_SHORT).show();
            }
        });
    }




    //걸음 수 측정하도록 하는 센서이벤트
    private SensorEventListener stepDetector = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {//러닝할 때 이동이 감지 되는 경우
            //x, y, z축
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            currentY = y;
            if (time_vision == 1){//오로지 러닝을 하는 경우에만 if문 실행
                if (Math.abs(currentY - previousY) > threshold) {//센서가 감지되는 기준(임계치 5)
                    
                    //거리 계산하기
                    distance = (float) steps / 300;
                    distance = (float) (Math.round(distance * 100) / 100.0);
                    end_time = SystemClock.elapsedRealtime();//종료 시간
                    //시간을 ms에서 hour로 바꾸기
                    time = ((float)(end_time - start_time) / 1000 / 60 / 60);
                    average_speed = Math.round(distance / time) * 1.0;//평균 속력 구하기
                    
                    //러닝 관련 기록들 실시간으로 보여주기
                    stepsTextView.setText(String.valueOf(steps));
                    distanceView.setText(String.valueOf(distance) + "km");
                    averageSpeedView.setText(String.valueOf(average_speed) + "km/h");
                    steps++;
                }
            }
            //센서가 감지되고 있는 것을 화면에 보여주기
            gx.setText(String.valueOf(x));
            gy.setText(String.valueOf(y));
            gz.setText(String.valueOf(z));

            previousY = y;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    //리셋 버튼 누른 경우
    public void resetSteps(View v) {
        steps = 0;
        stepsTextView.setText(String.valueOf(steps));
    }

    public void setStart(View view){

    }

    //센서의 민감도 조절하는 부분이 바뀌는 것에 대한 이벤트 처리
    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//이벤트 처리
            threshold = seekBar.getProgress();//시크바에 보여주기
            sensitiveTextView.setText(String.valueOf(threshold));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };




}



