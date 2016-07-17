package com.junior.dwan.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ToggleButton;

public class GameActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mAnswerButton;
    private TextView tvAnswer;
    private TextView tvScore;
    private TextView mQuestionTextView;
    private boolean mIsCheater;
    private boolean answerIsTrue;
    private static final String KEY_INDEX = "index";
    private static final String KEY_INTENT_DATA = "intentData";
    public static final String KEY_SCORE = "score";


    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private int score = 0;

    private void checkContinue() {
        Intent i = getIntent();
        if (i.getIntExtra(FirstActivity.EXTRA_START_GAME, 0) == -1) mCurrentIndex = 0;
        else mCurrentIndex = i.getIntExtra(FirstActivity.EXTRA_START_GAME, 0);
    }


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        mTrueButton.setBackgroundResource(R.drawable.main_background);
        mFalseButton.setBackgroundResource(R.drawable.main_background);
        mAnswerButton.setEnabled(true);
        mIsCheater = false;
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int mesResId = 0;
        if (mIsCheater) {
            if (userPressedTrue == answerIsTrue) {
                mesResId = R.string.earned_1;
                score += 1;
                tvScore.setText("Score: " + score);
                tvAnswer.setText(mesResId);

            } else {
                mesResId = R.string.earned_0;
                tvScore.setText("Score: " + score);
                tvAnswer.setText(mesResId);
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                mesResId = R.string.earned_2;
                score += 2;
                tvScore.setText("Score: " + score);
                tvAnswer.setText(mesResId);
            } else {
                mesResId = R.string.earned_0;
                tvScore.setText("Score: " + score);
                tvAnswer.setText(mesResId);
            }
        }
//        Toast.makeText(this, mesResId, Toast.LENGTH_SHORT).show();
    }



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
                    checkAnswer(true);
                mTrueButton.setBackgroundResource(R.drawable.main_background);
                    mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                    updateQuestion();

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkAnswer(false);
                mFalseButton.setBackgroundResource(R.drawable.main_background);

                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                    updateQuestion();
            }
        });
        tvScore=(TextView)findViewById(R.id.tvScore);
        tvScore.setText("Score: " + score);
        tvAnswer=(TextView)findViewById(R.id.tvPoint);
        mAnswerButton = (Button) findViewById(R.id.btnAnswer);
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                if(answerIsTrue) {

                    mTrueButton.setBackgroundResource(R.drawable.answer_btn);
                    mAnswerButton.setEnabled(false);
//                    tvAnswer.setText(R.string.true_button);
                    mIsCheater=true;
                }else {

                    mAnswerButton.setEnabled(false);
//                    tvAnswer.setText(R.string.false_button);
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
