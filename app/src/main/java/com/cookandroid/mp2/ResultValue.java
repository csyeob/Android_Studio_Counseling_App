package com.cookandroid.mp2;

import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ResultValue {

    public double value;
    public String answer;
    public double value_av []  = {-1.8, -1.7,-1.6,-1.5,-1.4,-1.3,-1.2,-1.1,-1.0,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1,0,
            0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8};
    public int score [] = {10,13,17,20,22,24,26,28,30,
            32,34,36,38,40,42,44,46,48,
            50,52,54,56,58,60,62,64,66,
            68,70,72,74,76,78,80,82,85,90};
    public double chart_value [] = {-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1,0,
                                    0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
    public int chart_score [] = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190};

    public double value_substring(String question){
        if(question.substring(0,4).contains("}")){
            value = Double.parseDouble(question.substring(0,3));
        }else{
            value = Double.parseDouble(question.substring(0,4)); // 가족 . 관심
        }
        return value;
    }
    public String value_answer(double value, int number_question){
        switch (number_question){
            case 1:
                if(value >= -0.9 && value <=-0.1){
                    answer = "부모님의 관심이 많은 것에 비해, 그 관심이 자녀에게 불편한 감정으로 느껴지는 것 같습니다. " +
                            "\n"+"대화를 유도하는 것 보다는" +
                            "대화를 좀 줄이며 관심에 척도를 줄이는 것이 좋아 보입니다.";
                }else {
                    answer = "부모님의 관심이 자녀에게 긍정적으로 유도되는 것 같습니다. " +
                            "\n"+ "앞으로도 이와같이 적절한 관계를 유지하는 것이 좋아보입니다.";
                }
                return answer;
            case 2:
                if(value >= -0.9 && value <=-0.1){
                    answer = "부모님의 다툼과 언성들이 자녀에게 부정적인 영향을 끼치는 것 같습니다. "
                            + "불가피한 다툼이라도 자녀 앞에서는 삼가하는게 좋아보입니다.";
                }else{
                    answer = "부모님의 관계가 자녀의 입장에도 좋게 보이며, 긍정적인 영향을 받는 것 같습니다. " +
                            "\n"+"현재와 같이 좋은 관계를" +
                            "자녀에게 보여주며 자녀와의 연결점을 찾아 가는 것도 더 좋은 영향을 줄 수 있을 것 같습니다.";
                }
                return answer;
            case 3:
                if(value >= -0.9 && value <=-0.1){
                    answer = "성적 점수나 학업 경쟁에 관해 극도로 예민합니다." +
                            "\n"+"그에 따라 심리적 안정감 등 조치가 필요합니다.";
                }else{
                    answer = "점수에 대해 크게 연연하지 않지만, 학업에 대한 욕심이 있다면, " +
                            "\n"+"그에 맞는 대비책이 필요합니다.";
                }
                return answer;
            case 4:
                if(value >= -0.9 && value <=-0.1){
                    answer = "자신의 진학에 대한 걱정이 많고 진로의 방향성을 찾지 못하는 것 같습니다. 자녀의 진로관하여 보호자와의 대화가 \n" +
                            " 자녀와의 대화로 진로의 방향성을 정해주는 것이 필요해 보입니다. ";
                }else{
                    answer = "진로에 대한 걱정이 딱히 없습니다. 하지만 자신감에 비하여 원하는 결과가 나오지 않을 수 있어" +
                            " 어느정도의 긴장감이 필요해 보입니다.";
                }
                return answer;
            case 5:
                if(value >= -0.9 && value <=-0.1){
                    answer = "학교폭력 및 따돌림을 당하고 있는 것 같습니다. 그에 따른 조치가 시급하며, 혹여나 말하지 못 할 문제들도\n" +
                            " 추가적으로 있을 수 있으니 속 깊은 대화와 조치를 통해 안정감을 심어주고, 문제 해결이 필요합니다. ";
                }else{
                    answer = "학교폭력 및 따돌림 없이 교우 관계에 큰 문제없이 잘 유지하고 있는 것 같습니다.";
                }
                return answer;
            case 6:
                if(value >= -0.9 && value <=-0.1){
                    answer = "외부적인 폭력이나 따돌림등은 없으나, 교우 무리 안에 있어서 자신만의 소외감을 느끼는 것 같습니다.\n" +
                            "이런 소외감은 대외적으로 말하기가 힘드니, 편한상태를 유지시켜주면서 대화를 유도하는게 좋을 것 같습니다.";
                }else{
                    answer = "폭력이나 따돌림도 없고, 함께 지내는 교우 무리 안에서도 큰 탈 없이 잘 지내는 것 같습니다. 이대로 잘\n" +
                            "생활 할 수 있도록 약간의 관심 정도면 적당할 것 같습니다.";
                }
                return answer;
        }
        return answer;
    }

    public int score_average(double value1, double value2){
        double value;
        int k = 0;
        value = value1+ value2;
        for(int i= 0; i<value_av.length; i++){
            if( value ==value_av[i]){
                k = i;
            }
        }
        return score[k];
    }

    public int chart_result(double value){
        int a = 0;
        for(int i = 0; i< chart_value.length; i++){
            if(value == chart_value[i]){
                a = i;
            }
        }
        return chart_score[a];
    }
    public String chart_Result(int chartScore1, int chartScore2, int chartScore3, int chartScore4, int chartScore5, int chartScore6){
        String s = "차트는 점수가 높을수록 긍정에 가깝고, 짧을수록 부정에 가깝습니다. 점수는 10점 부터 190점까지 입니다.\n\n"+
                "가족 항목부분"+ "\n부부싸움 항목에 "+chartScore1+"점을 받았습니다."
                +"\n관심도 항목에서는 "+chartScore2+"점을 받았습니다."
                +"\n친구 항목부분"+ "\n폭력 및 따돌림 항목에 "+chartScore3+"점을 받았습니다."
                +"\n소외감 항목에서는 "+chartScore4+"점을 받았습니다."
                +"\n학업 항목부분"+ "\n시험스트레스 항목에 "+chartScore5+"점을 받았습니다."
                +"\n진학 항목에서는 "+chartScore6+"점을 받았습니다.";

                return s;
    }
}
