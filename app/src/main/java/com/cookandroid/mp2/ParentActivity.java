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
    DataBase_User_Info dataBase_user_info;
    private int query = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        dataBase_user_info = new DataBase_User_Info();
        dataBase_user_info.start_db();
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
                // 0 전번 , 1 이름,  2 생일, 3 id, 4 자녀 부모, 5 pwd
                    if (birth.getText().toString().length() == 0 || child_name.getText().toString().length() == 0 || phone_number.getText().toString().length() == 0) {
                        String msg = "자식아이디, 생년월일, 핸드폰번호를 빠짐없이 기입해주세요.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } else {
                        for(int i =0; i<dataBase_user_info.Array_id.length; i++) {
                            if (dataBase_user_info.Array_id[i].contains("phone="+phone_number.getText().toString()+",") &&
                                    dataBase_user_info.Array_id[i+2].contains("birth="+birth.getText().toString()+",") &&
                                    dataBase_user_info.Array_id[i+3].contains("id="+Child_ID+",")&&
                                    dataBase_user_info.Array_id[i+4].contains("자녀")) {
                                query++;
                            }
                        }
                        if(query > 0) {
                            mDatabase.child("Users").child(id_loginAc).child("My_child").setValue(childInfo);
                            Intent intent = new Intent(ParentActivity.this, ParentResultActivity.class);
                            intent.putExtra("C_ID", Child_ID);
                            startActivity(intent);
                        }
                        else{
                            String msg = "자식정보를 정확히 입력 해주세요.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        });

    }
}