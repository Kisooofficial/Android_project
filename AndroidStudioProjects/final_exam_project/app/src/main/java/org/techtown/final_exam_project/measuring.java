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
    private TextView gx, gy, gz;
    private TextView stepsTextView, sensitiveTextView;
    private TextView distanceView, averageSpeedView;
    private Chronometer timeView;
    private Button reset, start, save, back, check;

    private SensorManager sensorManager;
    private float acceleration;
    private DB_helper db_helper;

    private Date today = new Date();
    private float previousY, currentY;
    private int steps;

    private float distance;
    private float time;

    double average_speed;
    long start_time = 0;
    long end_time;
    SeekBar seekBar;
    int threshold;
    long stopTime = 0;
    int time_vision = 0;
    String running_type = radio_button.running_type;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_measure);

        db_helper = new DB_helper(this);
        db_helper.open();
        db_helper.create();

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

        seekBar.setProgress(5);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        threshold = 5;
        sensitiveTextView.setText(String.valueOf(threshold));
        timeView = (Chronometer) findViewById(R.id.time_number);


        previousY = currentY = steps = 0;

        acceleration = 0.0f;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);



        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(measuring.this, MainActivity.class);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                time_vision = 1;
                start_time = SystemClock.elapsedRealtime();
                timeView.setBase(SystemClock.elapsedRealtime() + stopTime);
                timeView.start();
                start.setVisibility(View.GONE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                db_helper.insertColum(today, running_type, distance, average_speed);
                time_vision = 0;
                steps = 0;
                distance = 0;
                average_speed = 0;
                stepsTextView.setText(String.valueOf(steps));
                distanceView.setText(String.valueOf(distance));
                averageSpeedView.setText(String.valueOf(average_speed));
                timeView.setBase(SystemClock.elapsedRealtime());
                stopTime = 0;
                timeView.stop();
                start.setVisibility(View.VISIBLE);

            }
        });

        check.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "체크함", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private SensorEventListener stepDetector = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            currentY = y;
            if (time_vision == 1){
            if (Math.abs(currentY - previousY) > threshold) {
                distance = (float) steps / 300;
                distance = (float) (Math.round(distance * 100) / 100.0);
                //time = (SystemClock.elapsedRealtime()-timeView.getBase()) / 1000;
                //time = timeView.getBase() / 1000;
                end_time = SystemClock.elapsedRealtime();
                time = ((float)(end_time - start_time) / 1000 / 60 / 60);
                average_speed = Math.round(distance / time) * 1.0;
                stepsTextView.setText(String.valueOf(steps));
                distanceView.setText(String.valueOf(distance) + "km");
                averageSpeedView.setText(String.valueOf(average_speed) + "km/h");
                steps++;
            }
            }
            gx.setText(String.valueOf(x));
            gy.setText(String.valueOf(y));
            gz.setText(String.valueOf(z));

            previousY = y;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void resetSteps(View v) {
        steps = 0;
        stepsTextView.setText(String.valueOf(steps));
    }

    public void setStart(View view){

    }

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            threshold = seekBar.getProgress();
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



