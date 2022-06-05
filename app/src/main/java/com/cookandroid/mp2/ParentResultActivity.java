package com.cookandroid.mp2;

import static android.util.TypedValue.*;
import static androidx.constraintlayout.widget.ConstraintLayout.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.app.ActionBar;
import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentResultActivity extends AppCompatActivity {

    Button push;
    public String c_id;
    private DatabaseReference mDatabase;

    TextView total;
    TextView school_score;
    TextView friend_score;
    TextView family_score;
    TextView result_text;
    TextView result_text_chart;

    ImageView family1; ImageView family2;
    ImageView friend1; ImageView friend2;
    ImageView school1; ImageView school2;

    ImageView total_img;
    ImageView circle_img;

    DataBase_User_Info data = new DataBase_User_Info();
    private String array_result[];
    private String question1[]; private String question3[];private String question4[];
    private String question7[]; private String question9[];private String question10[];

    private double value1; private double value2; private double value3;
    private double value4; private double value5; private double value6;

    private String answer1; private String answer2; private String answer3;
    private String answer4; private String answer5; private String answer6;

    private int total_avg_score;
    private int school_avg_score; private int family_avg_score; private int friend_avg_score;

    private int family_chart1; private int family_chart2;
    private int school_chart1; private int school_chart2;
    private int friend_chart1; private int friend_chart2;

    ResultValue resultValue;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_result);

        // 자식id 받기
        Intent intent = getIntent();
        c_id = intent.getStringExtra("C_ID");

        // database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        data.FindDatabase(c_id);

        //xml_layout id 가져오기
        push = (Button) findViewById(R.id.button_push);
        total = (TextView) findViewById(R.id.total_score);
        school_score = (TextView) findViewById(R.id.school_score); school_score.setVisibility(View.INVISIBLE);
        family_score = (TextView) findViewById(R.id.family_score); family_score.setVisibility(View.INVISIBLE);
        friend_score = (TextView) findViewById(R.id.friend_score); friend_score.setVisibility(View.INVISIBLE);
        result_text = (TextView) findViewById(R.id.result_textView);
        result_text_chart = (TextView) findViewById(R.id.result_chart_text);

        family1 = (ImageView) findViewById(R.id.family1);
        family2 = (ImageView) findViewById(R.id.family2);
        friend1 = (ImageView) findViewById(R.id.friend1);
        friend2 = (ImageView) findViewById(R.id.friend2);
        school1 = (ImageView) findViewById(R.id.schoool1);
        school2 = (ImageView) findViewById(R.id.school2);

        scrollView = (ScrollView) findViewById(R.id.scrollView2);
        scrollView.setVisibility(View.INVISIBLE);
        total_img = (ImageView)findViewById(R.id.imageview_total);
        circle_img = (ImageView) findViewById(R.id.imageview_circle);


        total_img.setVisibility(View.INVISIBLE);
        circle_img.setVisibility(View.INVISIBLE);
        total.setVisibility(View.INVISIBLE);
        // 상담결과 calss 인스턴스
        resultValue = new ResultValue();

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_img.setVisibility(View.VISIBLE);
                circle_img.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                school_score.setVisibility(View.VISIBLE);
                friend_score.setVisibility(View.VISIBLE);
                family_score.setVisibility(View.VISIBLE);

                array_result = data.result.split(",");
                question1 = array_result[1].split("="); question3 = array_result[3].split("=");
                question4 = array_result[4].split("="); question7 = array_result[7].split("=");
                question9 = array_result[9].split("="); question10 = array_result[10].split("=");
                //결과 값

                value1 = resultValue.value_substring(question7[2]); value2 = resultValue.value_substring(question1[2]); // 가족
                value3 = resultValue.value_substring(question3[2]); value4 = resultValue.value_substring(question9[2]);// 친구
                value5 = resultValue.value_substring(question4[2]); value6 = resultValue.value_substring(question10[2]); // 학업

                answer1 = resultValue.value_answer(value1, 1); answer2 = resultValue.value_answer(value2, 2);
                answer3 = resultValue.value_answer(value3, 3); answer4 = resultValue.value_answer(value4, 4);
                answer5 = resultValue.value_answer(value5, 5); answer6 = resultValue.value_answer(value6, 6);

                family_avg_score = resultValue.score_average(value1, value2);
                school_avg_score = resultValue.score_average(value3, value4);
                friend_avg_score = resultValue.score_average(value5, value6);
                total_avg_score = (int)((family_avg_score + school_avg_score + friend_avg_score) / 3);

                family_chart1 = resultValue.chart_result(value2);
                family_chart2 = resultValue.chart_result(value1);
                school_chart1 = resultValue.chart_result(value5);
                school_chart2 = resultValue.chart_result(value6);
                friend_chart1 = resultValue.chart_result(value3);
                friend_chart2 = resultValue.chart_result(value4);


                ViewGroup.LayoutParams layoutParams_f1 = family1.getLayoutParams();
                layoutParams_f1.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, family_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_f2 = family2.getLayoutParams();
                layoutParams_f2.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, family_chart2, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_s1 = school1.getLayoutParams();
                layoutParams_s1.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, school_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_s2 = school2.getLayoutParams();
                layoutParams_s2.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, school_chart2, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_d1 = friend1.getLayoutParams();
                layoutParams_d1.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, friend_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_d2 = friend2.getLayoutParams();
                layoutParams_d2.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, friend_chart2, getResources().getDisplayMetrics());
                family1.setLayoutParams(layoutParams_f1);
                family2.setLayoutParams(layoutParams_f2);
                school1.setLayoutParams(layoutParams_s1);
                school2.setLayoutParams(layoutParams_s2);
                friend1.setLayoutParams(layoutParams_d1);
                friend2.setLayoutParams(layoutParams_d2);


                total.setText(Integer.toString(total_avg_score)+"점");
                school_score.setText(Integer.toString(school_avg_score)+"점");
                family_score.setText(Integer.toString(family_avg_score)+"점");
                friend_score.setText(Integer.toString(friend_avg_score)+"점");

                result_text_chart.setText(resultValue.chart_Result(family_chart1,family_chart2,friend_chart1,friend_chart2,school_chart1,school_chart2));
                result_text.setText("가족\n 1. "+answer1+"\n 2."+answer2+
                                    "\n\n친구\n 1. "+answer3+"\n 2."+answer4+
                                    "\n\n학업\n 1. "+answer5+"\n 2."+answer6);

            }

        });





    }
}