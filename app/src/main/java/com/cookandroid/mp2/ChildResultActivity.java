package com.cookandroid.mp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChildResultActivity extends AppCompatActivity {

    Button result_final;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_result);

        result_final = (Button) findViewById(R.id.button_reuslt_final);

        result_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(),AnswerActivity.class);
                startActivity(intent);
            }
        });
    }
}