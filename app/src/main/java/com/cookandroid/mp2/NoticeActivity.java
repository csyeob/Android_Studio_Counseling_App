package com.cookandroid.mp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoticeActivity extends AppCompatActivity {
    Button start_notice_btn;
    String CID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Intent intent =getIntent();
        CID = intent.getStringExtra("ID");
        start_notice_btn = (Button) findViewById(R.id.button_Start);

        start_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                intent.putExtra("ID", CID);
                startActivity(intent);
            }
        });
    }
}