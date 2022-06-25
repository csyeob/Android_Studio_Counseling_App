package com.cookandroid.mp2;

import static androidx.constraintlayout.widget.ConstraintLayout.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.assist.AssistStructure;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ParentResultActivity<canvas> extends AppCompatActivity {

    Button push;
    Button pdf;

    public String c_id;
    private DatabaseReference mDatabase;

    //pdf save버튼 코딩
    private LinearLayout linear;
    private Bitmap bitmap;
    private ConstraintLayout constraintLayout;

    TextView total;
    TextView school_score;
    TextView friend_score;
    TextView family_score;
    TextView result_text;
    TextView result_text_chart;

    ImageView family1;
    ImageView family2;
    ImageView friend1;
    ImageView friend2;
    ImageView school1;
    ImageView school2;

    ImageView total_img;
    ImageView circle_img;

    DataBase_User_Info data = new DataBase_User_Info();
    private String array_result[];
    private String question1[];
    private String question3[];
    private String question4[];
    private String question7[];
    private String question9[];
    private String question10[];

    private double value1;
    private double value2;
    private double value3;
    private double value4;
    private double value5;
    private double value6;

    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private String answer6;

    private int total_avg_score;
    private int school_avg_score;
    private int family_avg_score;
    private int friend_avg_score;

    private int family_chart1;
    private int family_chart2;
    private int school_chart1;
    private int school_chart2;
    private int friend_chart1;
    private int friend_chart2;

    ResultValue resultValue;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_result);
        //pdf
        constraintLayout = findViewById(R.id.constraint_layout);

        // 자식id 받기
        Intent intent = getIntent();
        c_id = intent.getStringExtra("C_ID");

        // database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        data.FindDatabase(c_id);

        //xml_layout id 가져오기
        push = (Button) findViewById(R.id.button_push);
        pdf = (Button) findViewById(R.id.save_pdf);
        total = (TextView) findViewById(R.id.total_score);
        school_score = (TextView) findViewById(R.id.school_score);
        school_score.setVisibility(View.INVISIBLE);
        family_score = (TextView) findViewById(R.id.family_score);
        family_score.setVisibility(View.INVISIBLE);
        friend_score = (TextView) findViewById(R.id.friend_score);
        friend_score.setVisibility(View.INVISIBLE);
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
        total_img = (ImageView) findViewById(R.id.imageview_total);
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
                question1 = array_result[1].split("=");
                question3 = array_result[3].split("=");
                question4 = array_result[4].split("=");
                question7 = array_result[7].split("=");
                question9 = array_result[9].split("=");
                question10 = array_result[10].split("=");
                //결과 값
                value1 = resultValue.value_substring(question7[2]);
                value2 = resultValue.value_substring(question1[2]); // 가족
                value3 = resultValue.value_substring(question3[2]);
                value4 = resultValue.value_substring(question9[2]);// 친구
                value5 = resultValue.value_substring(question4[2]);
                value6 = resultValue.value_substring(question10[2]); // 학업

                answer1 = resultValue.value_answer(value1, 1);
                answer2 = resultValue.value_answer(value2, 2);
                answer3 = resultValue.value_answer(value3, 3);
                answer4 = resultValue.value_answer(value4, 4);
                answer5 = resultValue.value_answer(value5, 5);
                answer6 = resultValue.value_answer(value6, 6);

                family_avg_score = resultValue.score_average(value1, value2);
                school_avg_score = resultValue.score_average(value3, value4);
                friend_avg_score = resultValue.score_average(value5, value6);
                total_avg_score = (int) ((family_avg_score + school_avg_score + friend_avg_score) / 3);

                family_chart1 = resultValue.chart_result(value2);
                family_chart2 = resultValue.chart_result(value1);
                school_chart1 = resultValue.chart_result(value5);
                school_chart2 = resultValue.chart_result(value6);
                friend_chart1 = resultValue.chart_result(value3);
                friend_chart2 = resultValue.chart_result(value4);


                ViewGroup.LayoutParams layoutParams_f1 = family1.getLayoutParams();
                layoutParams_f1.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, family_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_f2 = family2.getLayoutParams();
                layoutParams_f2.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, family_chart2, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_s1 = school1.getLayoutParams();
                layoutParams_s1.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, school_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_s2 = school2.getLayoutParams();
                layoutParams_s2.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, school_chart2, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_d1 = friend1.getLayoutParams();
                layoutParams_d1.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, friend_chart1, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams layoutParams_d2 = friend2.getLayoutParams();
                layoutParams_d2.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, friend_chart2, getResources().getDisplayMetrics());
                family1.setLayoutParams(layoutParams_f1);
                family2.setLayoutParams(layoutParams_f2);
                school1.setLayoutParams(layoutParams_s1);
                school2.setLayoutParams(layoutParams_s2);
                friend1.setLayoutParams(layoutParams_d1);
                friend2.setLayoutParams(layoutParams_d2);


                total.setText(Integer.toString(total_avg_score) + "점");
                school_score.setText(Integer.toString(school_avg_score) + "점");
                family_score.setText(Integer.toString(family_avg_score) + "점");
                friend_score.setText(Integer.toString(friend_avg_score) + "점");

                result_text_chart.setText(resultValue.chart_Result(family_chart1, family_chart2, friend_chart1, friend_chart2, school_chart1, school_chart2));
                result_text.setText("가족\n 1. " + answer1 + "\n 2." + answer2 +
                        "\n\n친구\n 1. " + answer3 + "\n 2." + answer4 +
                        "\n\n학업\n 1. " + answer5 + "\n 2." + answer6);
                push.setVisibility(View.INVISIBLE);
            }

        });
        pdf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("size", "" +3000 + " " + 3000);
                bitmap = LoadBitmap(scrollView, scrollView.getWidth(), 3050);
                createPdf();
            }
        });
    }
    private void openPdf() {
        File file = new File("/sdcard/page.pdf");
        if (file.exists()) {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent1.setDataAndType(uri, "application/pdf");
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent1);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No Application for pdf view", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Bitmap LoadBitmap (View v,int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    private void createPdf () {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        File filedown = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String targetPdf = filedown.getPath()+"/"+c_id+"의 상담결과.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "에러: " + e.toString(), Toast.LENGTH_LONG).show();
        }////////////////////

        // close the document
        document.close();
        Toast.makeText(this, "pdf 받기 성공", Toast.LENGTH_SHORT).show();
        openPdf();

    }
}