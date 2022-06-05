package com.cookandroid.mp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by js on 2017-03-18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private String login_instance_context; // login한 사용자의 리스트를 가져오기 위함.
    private ArrayList<AnswerActivity.item> mDataset; //MainActivity에 item class를 정의해 놓았음

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mQuestion;
        public TextView mAnswer;
        public TextView mScore;

        public ViewHolder(View v) {
            super(v);

            mQuestion = (TextView) v.findViewById(R.id.question_txt);
            mAnswer = (TextView) v.findViewById(R.id.answer_txt);
            mScore = (TextView) v.findViewById(R.id.score_txt);


        }
    }

    // 생성자 - 넘어 오는 데이터파입에 유의해야 한다.
    public Adapter(ArrayList<AnswerActivity.item> myDataset) {
        mDataset = myDataset;
    }


    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mQuestion.setText(mDataset.get(position).getQuestion());
        holder.mAnswer.setText(mDataset.get(position).getAnswer()); //int를 가져온다는점 유의
        holder.mScore.setText(mDataset.get(position).getScore());
        //holder.mPhoto.setImageBitmap(mDataset.get(position).getPhoto()); //첨부된 이미지를 연결해줘야 하는데 이건 또 복잡하다. 이건 나중에...

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

