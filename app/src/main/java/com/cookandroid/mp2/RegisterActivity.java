package com.cookandroid.mp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText _id;
    EditText pwd;
    EditText phone;
    EditText name;
    EditText birth;
    String family ="";
    EditText pwd_check;

    Button assign;
    Button parent_btn;
    Button child_btn;
    Button overlap_btn;

    private int p = 0;
    DataBase_User_Info dataBase_user_info;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        _id =    (EditText) findViewById(R.id.editText_Assign_ID);
        pwd =   (EditText) findViewById(R.id.editText_Assign_Password);
        name = (EditText) findViewById(R.id.editText_Assign_name);
        birth = (EditText) findViewById(R.id.editText_Assign_Birth);
        phone = (EditText) findViewById(R.id.editTextAssign_PhoneNumber);
        pwd_check = (EditText) findViewById(R.id.editText_Assign_Password_check);

        assign = (Button) findViewById(R.id.assign_button);
        parent_btn = (Button) findViewById(R.id.parent_btn);
        child_btn = (Button) findViewById(R.id.child_btn);
        overlap_btn = (Button) findViewById(R.id.overlap_btn);


        dataBase_user_info = new DataBase_User_Info();
        dataBase_user_info.start_db();

        parent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                family = "부모";
                parent_btn.setBackgroundResource(R.drawable.button_check);
            }
        });

        child_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                family = "자녀";
                child_btn.setBackgroundResource(R.drawable.button_check);
            }
        });

        overlap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = 0;
                for(int i =0; i<dataBase_user_info.Array_id.length; i++) {
                    if (dataBase_user_info.Array_id[i].contains("id=" + _id.getText().toString() + ",")) {
                        String msg = "이미 사용하고 있는 아이디입니다.";
                        p++;
                    }
                }
                if(p > 0){
                    String msg = "이미 사용하고 있는 아이디입니다.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    assign.setEnabled(false);
                }
                else{
                    String msg = "아이디 사용가능.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    assign.setEnabled(true);
                }
            }
        });
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_id.getText().toString().length() == 0 || pwd.getText().toString().length() == 0  || name.getText().toString().length() == 0
                        || birth.getText().toString().length() == 0 || phone.getText().toString().length() == 0){
                    String msg = "아이디, 비밀번호,이름, 생년월일, 핸드폰번호를 빠짐없이 기입해주세요.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else {
                    if(family.length() == 0){
                        String msg = "계정 선택 버튼을 눌러주세요.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }else {
                        if (pwd.getText().toString().equals(pwd_check.getText().toString())) {
                            UserInfo userdata = new UserInfo(_id.getText().toString(), pwd.getText().toString(), name.getText().toString(),
                                    birth.getText().toString(), phone.getText().toString(), family.toString());
                            myRef.child("Users").child(userdata.id).setValue(userdata);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            String msg = "비밀번호를 확인해주세요.";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }

}