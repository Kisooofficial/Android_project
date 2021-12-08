package org.techtown.final_exam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class radio_button extends AppCompatActivity {
    public static String running_type = "r";
    Button radioButton;
    Button cancelButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_measuring_button);

        radioButton = (Button) findViewById(R.id.start);
        cancelButton = (Button) findViewById(R.id.cancel);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(radio_button.this, measuring.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(radio_button.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
    public void clicked(View v){
        if(v.getId() == R.id.walking) running_type = "w";
    }

}
