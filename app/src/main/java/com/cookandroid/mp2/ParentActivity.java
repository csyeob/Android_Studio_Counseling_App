package com.cookandroid.mp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentActivity extends AppCompatActivity {

    EditText birth;
    EditText child_name;
    EditText phone_number;
    Button child_assign;

    private DatabaseReference mDatabase;
    private String id_loginAc;

    public String Child_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        birth = (EditText) findViewById(R.id.Birth);
        child_name = (EditText) findViewById(R.id.Child_Name);
        phone_number = (EditText) findViewById(R.id.PhoneNumber);
        child_assign = (Button) findViewById(R.id.cild_assign);



        Intent intent = getIntent();
        id_loginAc = intent.getStringExtra("P_ID");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        child_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChildInfo childInfo = new ChildInfo(child_name.getText().toString(), phone_number.getText().toString(), birth.getText().toString());
                Child_ID = child_name.getText().toString();
                if(birth.getText().toString().length() == 0 || child_name.getText().toString().length() == 0 || phone_number.getText().toString().length() == 0){
                    String msg = "자식아이디, 생년월일, 핸드폰번호를 빠짐없이 기입해주세요.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }else{
                    mDatabase.child("Users").child(id_loginAc).child("My_child").setValue(childInfo);

                    Intent intent = new Intent(ParentActivity.this, ParentResultActivity.class);
                    intent.putExtra("C_ID", Child_ID);
                    startActivity(intent);
                }

            }
        });

    }
}