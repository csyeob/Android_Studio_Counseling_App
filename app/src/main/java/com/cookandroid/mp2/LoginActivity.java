package com.cookandroid.mp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    EditText login_id;
    EditText login_pwd;

    Button login_btn;
    Button register_btn;

    private String value;
    public String login_id_instance;
    public static Context context_id;
    private String array_login_result [];
    private DatabaseReference mDatabase;
    //private String pr;
    public String check_info;
    public String check_info_pwd;
    DataBase_User_Info dataBase_user_info;

    private int a = 2;
    private int b = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        //FriebaseDatabase.getInstance("https://mobileproject-7e642-default-rtdb.firebaseio.com")
        //이렇게 써도됨.
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        */
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dataBase_user_info = new DataBase_User_Info();
        dataBase_user_info.start_db();

        login_id = (EditText) findViewById(R.id.editText_id);
        login_pwd = (EditText) findViewById(R.id.editText_password);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.assign_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_info = "";
                check_info_pwd = "";
                check_info = login_id.getText().toString();
                check_info_pwd = login_pwd.getText().toString();

                // 0 전번 , 1 이름,  2 생일, 3 id, 4 자녀 부모, 5 pwd
                for(int i =0; i<dataBase_user_info.Array_id.length; i++){
                    if(dataBase_user_info.Array_id[i].contains("id="+check_info+",") && dataBase_user_info.Array_id[i+2].contains("pwd="+check_info_pwd+"}")
                    && dataBase_user_info.Array_id[i+1].contains("자녀")){
                        a++;
                    }
                    if(dataBase_user_info.Array_id[i].contains("id="+check_info+",") && dataBase_user_info.Array_id[i+2].contains("pwd="+check_info_pwd+"}")
                            && dataBase_user_info.Array_id[i+1].contains("부모")){
                        b++;
                    }
                }
                if(a > 2){
                        String msg = "로그인 성공";
                      Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, NoticeActivity.class);
                        intent.putExtra("ID", check_info);
                        startActivity(intent);
                }
                else {
                    if(b == 2){
                    String msg = "아이디, 비밀번호를 정확히 적어주세요.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                }
                if(b > 2){
                    String msg = "로그인 성공";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(LoginActivity.this, ParentActivity.class);
                    intent2.putExtra("P_ID", check_info);
                    startActivity(intent2);
                }
                else{
                    if(a==2) {
                        String msg = "아이디, 비밀번호를 정확히 적어주세요.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
//    public String FindDatabase(String login,String key){
//        mDatabase.child(login).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                pr = value;
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("MainActivity", String.valueOf(databaseError.toException()));
//            }
//        });
//        return pr;
//    }

}