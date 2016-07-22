package com.junior.dwan.geoquiz;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity implements View.OnClickListener {


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mAnswerButton;
    private TextView tvPoint;
    private TextView tvScore;
    private TextView mQuestionTextView;
    private TextView tvCountFacts;
    private boolean mIsCheater;
    private boolean answerIsTrue;
    private ArrayList<Fact> mFacts;
    private int mCurrentIndex=0;
    private int score=0;
    public static final String KEY_INDEX="index";
    public static final String KEY_SCORE="score";
    public static final String KEY_ANSWER="answer";
    public static final String KEY_CHEAT ="cheat" ;
    public static final String KEY_POINT ="point" ;
    public static final String TAG ="tag";
    private int mesResId=0;
    private int mCountFacts;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("tag", "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE,score);
        savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
        savedInstanceState.putBoolean(KEY_ANSWER,answerIsTrue);
        savedInstanceState.putInt(KEY_POINT,mesResId);
    }

    private void checkContinue() {
        Intent i = getIntent();
        if (i.getIntExtra(FirstActivity.EXTRA_START_GAME, 0) == -1) mCurrentIndex = 0;
        else mCurrentIndex = i.getIntExtra(FirstActivity.EXTRA_START_GAME, 0);
    }

    private void updateQuestion() {
            if(mCurrentIndex<50) {
                String question = mFacts.get(mCurrentIndex).getQuestion();
                mQuestionTextView.setText(question);
                mTrueButton.setBackgroundResource(R.drawable.btn_background);
                mFalseButton.setBackgroundResource(R.drawable.btn_background);
                mCountFacts = mCurrentIndex + 1;
                tvCountFacts.setText(mCountFacts + " / " + mFacts.size());
                checkAnswerColor();
                mAnswerButton.setEnabled(true);
                mIsCheater = false;

                Log.i(TAG, "update");
            } else {
        Intent i=new Intent(this,PassActivity.class);
                i.putExtra(PassActivity.EXTRA_SCORE,score);
        startActivity(i);
        }
    }

    private void checkAnswerColor(){
        if(mFacts.get(mCurrentIndex).isTrueQuestion()) mFalseButton.setBackgroundResource(R.drawable.btn_background_false);
        else mTrueButton.setBackgroundResource(R.drawable.btn_background_false);
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mFacts.get(mCurrentIndex).isTrueQuestion();
//        mesResId = 0;
        if (mIsCheater) {
            if (userPressedTrue == answerIsTrue) {
                mesResId = R.string.earned_1;
                score += 1;
                tvScore.setText("Score: " + score);
                tvPoint.setText(mesResId);
            } else {
                mesResId = R.string.earned_0;
                tvScore.setText("Score: " + score);
                tvPoint.setText(mesResId);
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                mesResId = R.string.earned_2;
                score += 2;
                tvScore.setText("Score: " + score);
                tvPoint.setText(mesResId);
            } else {
                mesResId = R.string.earned_0;
                tvScore.setText("Score: " + score);
                tvPoint.setText(mesResId);
            }
        }
    }

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mFacts=FactLab.getInstance(this).getFacts();

        mQuestionTextView = (TextView) findViewById(R.id.tvQuestions);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        tvScore=(TextView)findViewById(R.id.tvScore);
        tvPoint =(TextView)findViewById(R.id.tvPoint);
        mAnswerButton = (Button) findViewById(R.id.btnAnswer);
        tvCountFacts=(TextView)findViewById(R.id.tvCountFacts);

        updateQuestion();
        Log.i(TAG, "--------------------------------");
        Log.i(TAG,"boolean"+ mIsCheater);
        // активация кнопки home
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(this)!=null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setSubtitle("TFFT");
            }
        }

        //load data
       loadSavedInstanceState(savedInstanceState);


        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mAnswerButton.setOnClickListener(this);

    }

    private void loadSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX);
            score=savedInstanceState.getInt(KEY_SCORE);
            mIsCheater=savedInstanceState.getBoolean(KEY_CHEAT);
            answerIsTrue=savedInstanceState.getBoolean(KEY_ANSWER);
            mesResId=savedInstanceState.getInt(KEY_POINT);
            mCountFacts=mCurrentIndex+1;
            tvCountFacts.setText(mCountFacts+" / "+mFacts.size());
            Log.i(TAG,"loadData");
        } else {
            checkContinue();
        }
        checkAnswerColor();
        tvScore.setText("Score: " + score);
        mQuestionTextView.setText(mFacts.get(mCurrentIndex).getQuestion());
        if(mesResId==0){
            tvPoint.setText(R.string.point_textview);
        } else tvPoint.setText(mesResId);

        if(mIsCheater) {
            Log.i(TAG,"boolean if cheat"+ mIsCheater);
            if(answerIsTrue){
                onAnswerTrue(mTrueButton);
                Log.i(TAG,"boolean answer true");
            } else {
                onAnswerTrue(mFalseButton);
                Log.i(TAG, "boolean answer false");
            }
        } else Log.i(TAG, "boolean if mtrue" + mIsCheater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this)!=null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAnswerTrue(Button btn){
        mAnswerButton.setEnabled(false);
        mIsCheater=true;
        btn.setBackgroundResource(R.drawable.btn_background_answer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.true_button:
                checkAnswer(true);
                mCurrentIndex = (mCurrentIndex + 1);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                mCurrentIndex=(mCurrentIndex+1);
                updateQuestion();
                break;
            case R.id.btnAnswer:
                answerIsTrue = mFacts.get(mCurrentIndex).isTrueQuestion();
                if (answerIsTrue) onAnswerTrue(mTrueButton);
                else onAnswerTrue(mFalseButton);
                break;
            default:
                break;
        }
    }
}

