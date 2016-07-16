package com.junior.dwan.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class GameActivity extends Activity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mAnswerButton;
    private TextView tvAnswer;
    private TextView tvScore;
    private TextView mQuestionTextView;
    private boolean mIsCheater;
    private static final String KEY_INDEX = "index";
    private static final String KEY_INTENT_DATA = "intentData";
    public static final String KEY_SCORE="score";
    private boolean answerIsTrue;


    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex=0;
    private int score=0;
    private void checkContinue(){
        Intent i=getIntent();
       if(i.getIntExtra(FirstActivity.EXTRA_START_GAME,0)==-1) mCurrentIndex=0;
        else mCurrentIndex=i.getIntExtra(FirstActivity.EXTRA_START_GAME,0);
    }



    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        mTrueButton.setBackgroundResource(R.drawable.rect_button);
        mFalseButton.setBackgroundResource(R.drawable.rect_button);
        mAnswerButton.setEnabled(true);
        tvAnswer.setText("");
        mIsCheater=false;
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int mesResId = 0;
        if (mIsCheater) {
            if (userPressedTrue == answerIsTrue) {
                mesResId = R.string.judgment_toast;
                score+=1;
                tvScore.setText("Score: " + score);

            } else {
                mesResId = R.string.incorrect_judgement_toast;
                tvScore.setText("Score: " + score);
            }
        } else {
            if (userPressedTrue == answerIsTrue){
                mesResId = R.string.correct_toast;
                score+=2;

                tvScore.setText("Score: "+score);
            }
            else {
                mesResId = R.string.incorrect_toast;
                tvScore.setText("Score: " + score);
//                checkTrueColor();
            }
        }
        Toast.makeText(this, mesResId, Toast.LENGTH_SHORT).show();
    }

//    private void checkTrueColor(Boolean b) {
//        Boolean answe
//        Boolean check=
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mIsCheater = false;
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INTENT_DATA, false);
            score = savedInstanceState.getInt(KEY_SCORE,0);
        }
        score=0;
        mQuestionTextView = (TextView) findViewById(R.id.tvQuestions);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTrueButton.setBackgroundResource(R.drawable.choice_btn);
                    TimeUnit.MILLISECONDS.sleep(50);
                    checkAnswer(true);


                    mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                    updateQuestion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFalseButton.setBackgroundResource(R.drawable.choice_btn);
//                try {
//                    mFalseButton.setBackgroundResource(R.drawable.choice_btn);
//                    TimeUnit.SECONDS.wait(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    checkAnswer(false);
//                    mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
//                    updateQuestion();
//                }

            }
        });
        tvScore=(TextView)findViewById(R.id.tvScore);
        tvScore.setText("Score: "+score);
        tvAnswer=(TextView)findViewById(R.id.tvAnswer);
        mAnswerButton = (Button) findViewById(R.id.btnAnswer);
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                if(answerIsTrue) {
                    mTrueButton.setBackgroundResource(R.drawable.answer_btn);
                    mAnswerButton.setEnabled(false);
                    tvAnswer.setText(R.string.true_button);
                    mIsCheater=true;
                    try{
                        TimeUnit.SECONDS.wait(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mTrueButton.setBackgroundResource(R.drawable.choice_btn);
                    }

                }else {
                    mAnswerButton.setEnabled(false);
                    tvAnswer.setText(R.string.false_button);
                    mIsCheater=true;
                    mFalseButton.setBackgroundResource(R.drawable.answer_btn);
                }
            }
        });
        checkContinue();
        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_INTENT_DATA, mIsCheater);
        savedInstanceState.putInt(KEY_SCORE,score);
    }
}
