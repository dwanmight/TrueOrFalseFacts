package com.junior.dwan.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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
    private static int mCurrentIndex;
    private int score;
    public static final String KEY_INDEX = "index";
    public static final String KEY_SCORE = "score";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_CHEAT = "cheat";
    public static final String KEY_POINT = "point";
    public static final String TAG = "tag";
    public static final String SHARED_PREFERENCES = "sPref";
    public static final String SAVED_INDEX = "saved_index";
    public static final String SAVED_SCORE = "saved_score";
    public static final String SAVED_POINT = "saved_point";
    public static final String SAVED_PASS = "saved_pass";
    public static final String SAVED_CHEATER = "saved_cheater";
    public static final String SAVED_ANSWER = "saved_answer";
    private int mesResId = 0;
    private boolean pass;
    private int mCountFacts;
    private boolean mContCheater;
    private SharedPreferences mPreferences;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("tag", "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, score);
        savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
        savedInstanceState.putBoolean(KEY_ANSWER, answerIsTrue);
        savedInstanceState.putInt(KEY_POINT, mesResId);
        FactLab.getInstance(this).saveFacts();
    }

    private void checkContinue(Bundle bundle) {
        Intent i = getIntent();
        if (bundle != null) {
            mFacts = FactLab.getInstance(this).getFacts();
        } else if (i.getIntExtra(FirstActivity.EXTRA_START_GAME, 0) == 0) {
            mFacts = FactLab.getInstance(this).loadNewFact(this);
            Collections.shuffle(mFacts);
            score = 0;
            mCurrentIndex = 0;
        } else {
            mFacts = FactLab.getInstance(this).getFacts();
            mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            mCurrentIndex = mPreferences.getInt(SAVED_INDEX, 0);
            score = mPreferences.getInt(SAVED_SCORE, 0);
            mesResId = mPreferences.getInt(SAVED_POINT, 0);
            answerIsTrue = mPreferences.getBoolean(SAVED_ANSWER, false);
            mIsCheater = mPreferences.getBoolean(SAVED_CHEATER, false);
            if (mIsCheater) mContCheater = true;
            else mContCheater = false;
        }
    }

    private void updateQuestion() {
        if (mCurrentIndex >= 0 && mCurrentIndex < 50) {
            String question = mFacts.get(mCurrentIndex).getQuestion();
            mQuestionTextView.setText(question);
            mTrueButton.setBackgroundResource(R.drawable.btn_background);
            mFalseButton.setBackgroundResource(R.drawable.btn_background);
            mCountFacts = mCurrentIndex + 1;
            tvCountFacts.setText(mCountFacts + " / " + mFacts.size());
            checkAnswerColor();
            mAnswerButton.setEnabled(true);
            if (mContCheater) mIsCheater = true;
            else mIsCheater = false;
        } else {
            pass = false;
            Intent i = new Intent(this, PassActivity.class);
            i.putExtra(PassActivity.EXTRA_SCORE, score);
            startActivity(i);
            finish();
        }
    }

    private void checkAnswerColor() {
        if (mFacts.get(mCurrentIndex).isTrueQuestion())
            mFalseButton.setBackgroundResource(R.drawable.btn_background_false);
        else mTrueButton.setBackgroundResource(R.drawable.btn_background_false);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mFacts.get(mCurrentIndex).isTrueQuestion();
        mContCheater = false;
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
        pass = true;
        checkContinue(savedInstanceState);
        mQuestionTextView = (TextView) findViewById(R.id.tvQuestions);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        mAnswerButton = (Button) findViewById(R.id.btnAnswer);
        tvCountFacts = (TextView) findViewById(R.id.tvCountFacts);

        // activate button home
        getActionBar().setIcon(new ColorDrawable(0));
        if (NavUtils.getParentActivityName(this) != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //load data
        if (savedInstanceState != null) loadSavedInstanceState(savedInstanceState);
        else updateQuestion();
        checkAnswerColor();
        tvScore.setText("Score: " + score);
        mQuestionTextView.setText(mFacts.get(mCurrentIndex).getQuestion());
        if (mesResId == 0) {
            tvPoint.setText(R.string.point_textview);
        } else tvPoint.setText(mesResId);

        if (mIsCheater) {
            Log.i(TAG, "boolean if cheat" + mIsCheater);
            if (answerIsTrue) {
                onAnswerTrue(mTrueButton);
                Log.i(TAG, "boolean answer true");
            } else {
                onAnswerTrue(mFalseButton);
                Log.i(TAG, "boolean answer false");
            }
        } else Log.i(TAG, "boolean if mtrue" + mIsCheater);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mAnswerButton.setOnClickListener(this);
    }


    private void loadSavedInstanceState(Bundle savedInstanceState) {
        mFacts = FactLab.getInstance(this).getFacts();
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        score = savedInstanceState.getInt(KEY_SCORE);
        mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT);
        answerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER);
        mesResId = savedInstanceState.getInt(KEY_POINT);
        mCountFacts = mCurrentIndex + 1;
        tvCountFacts.setText(mCountFacts + " / " + mFacts.size());
        Log.i(TAG, "loadInstate");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAnswerTrue(Button btn) {
        mAnswerButton.setEnabled(false);
        mIsCheater = true;
        mContCheater = false;
        btn.setBackgroundResource(R.drawable.btn_background_answer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.true_button:
                checkAnswer(true);
                mCurrentIndex = (mCurrentIndex + 1);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                mCurrentIndex = (mCurrentIndex + 1);
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

    private void saveData() {
        mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(SAVED_INDEX, mCurrentIndex);
        editor.putInt(SAVED_SCORE, score);
        editor.putInt(SAVED_POINT, mesResId);
        editor.putBoolean(SAVED_PASS, pass);
        editor.putBoolean(SAVED_CHEATER, mIsCheater);
        editor.putBoolean(SAVED_ANSWER, answerIsTrue);
        editor.apply();
        Log.i(TAG, "saved pass " + pass);
        Log.i(TAG, "saved DATA GAME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        FactLab.getInstance(this).saveFacts();
        Log.i(TAG, "onPause");
    }
}

