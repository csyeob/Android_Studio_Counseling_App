package com.cookandroid.mp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.ArrayList;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnswerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<item> itemArrayList;

    Question_Answer question_answer = new Question_Answer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_answer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        //데이터준비-실제로는 ArrayList<>등을 사용해야 할듯 하다.
        //인터넷이나 폰에 있는 DB에서 아이템을 가져와 배열에 담아 주면 된다.
        //myDataset = new String[]{"도봉순", "이순신", "강감찬","세종대왕"};
        //ArrayList 생성
        itemArrayList = new ArrayList<>();
        //ArrayList에 값 추가하기
        //itemArrayList.add(new item("도봉순", 23, "dkdkdk@kdkdkd.com", null));
        for(int i = 0; i<question_answer.Question.length; i++){
            itemArrayList.add(new item(question_answer.Question[i], question_answer.Answer[i], question_answer.Result_Score[i]));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        mAdapter = new Adapter(itemArrayList); //스트링 배열 데이터 인자로
        mRecyclerView.setAdapter(mAdapter);
    }
    public class item {
       String question;
       String answer;
       String score;
        public item(String question, String answer, double score) {
          this.question = question;
          this.answer = answer;
          this.score = Double.toString(score);
        }

        public String getQuestion() {
            return question;
        }
        public String getAnswer(){
            return answer;
        }
        public String getScore(){ return score; }
    }
}