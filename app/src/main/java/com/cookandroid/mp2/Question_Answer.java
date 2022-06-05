package com.cookandroid.mp2;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Question_Answer{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static String  [] Question = {
            // 학업
            "시험 준비한 만큼 성적이 안 나올 때가 많니?"
            ,"성적이 준비한 만큼 나오지 않으면 가분이 어때?"
            ,"진학에 대한 고민이 있니?"
            ,"진학에 대한 어떤 고민이 있니?"
            // 친구
            ,"혹시 따돌림 혹은 학교폭력을 당한적 있니?"
            ,"따돌림 혹은 학교폭력 을 당할때 심정은 좀 어땠어?"
            ,"겉으로 잘어울리는 것처럼 보이지만 말 못할 소외감을 느껴본적있니?"
            ,"어떤 고민때문에 소외감이 느껴진다고 생각하니?"
            // 가족
            ,"부모님이 너에 대한 관심이 많은거같니?"
            ,"그 관심이 너한테 어떤 영향을 주니?"
            ,"부모님간의 서로 다툼이 많이 있는 편이니?"
            ,"다툼이 있다면 그럴때 넌 어떠한 행동을 취하고 그때의 심정은 어떠하니?"};

    public static String [] Answer    = new String[12];
    public static String [] Result_tx  = new String[12];
    public static double [] Result_Score = new double[12];

}
