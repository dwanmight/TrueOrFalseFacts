package com.junior.dwan.geoquiz.ui.activities;

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

import com.junior.dwan.geoquiz.data.managers.FactLab;
import com.junior.dwan.geoquiz.utils.ConstantManager;
import com.junior.dwan.geoquiz.utils.Fact;
import com.junior.dwan.geoquiz.R;

import java.util.ArrayList;
import java.util.Collections;

import static com.junior.dwan.geoquiz.utils.ConstantManager.KEY_ANSWER;
import static com.junior.dwan.geoquiz.utils.ConstantManager.KEY_CHEAT;
import static com.junior.dwan.geoquiz.utils.ConstantManager.KEY_INDEX;
import static com.junior.dwan.geoquiz.utils.ConstantManager.KEY_POINT;
import static com.junior.dwan.geoquiz.utils.ConstantManager.KEY_SCORE;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_ANSWER;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_CHEATER;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_INDEX;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_PASS;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_POINT;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SAVED_SCORE;
import static com.junior.dwan.geoquiz.utils.ConstantManager.SHARED_PREFERENCES;

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
    private  int mCurrentIndex;
    private int score;

    private int mesResId = 0;
    private boolean pass;
    private int mCountFacts;
    private boolean mContCheater;
    private SharedPreferences mPreferences;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("tag", "onSaveInstanceState");
        savedInstanceState.putInt(ConstantManager.KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(ConstantManager.KEY_SCORE, score);
        savedInstanceState.putBoolean(ConstantManager.KEY_CHEAT, mIsCheater);
        savedInstanceState.putBoolean(ConstantManager.KEY_ANSWER, answerIsTrue);
        savedInstanceState.putInt(ConstantManager.KEY_POINT, mesResId);
        FactLab.getInstance(this).saveFacts();
    }

    private void checkContinue(Bundle bundle) {
        Intent i = getIntent();
        if (bundle != null) {
            mFacts = FactLab.getInstance(this).getFacts();
        } else if (i.getIntExtra(ConstantManager.EXTRA_START_GAME, 0) == 0) {
            mFacts = FactLab.getInstance(this).loadNewFact(this);
            Collections.shuffle(mFacts);
            score = 0;
            mCurrentIndex = 0;
        } else {
            mFacts = FactLab.getInstance(this).getFacts();
            mPreferences = getSharedPreferences(ConstantManager.SHARED_PREFERENCES, MODE_PRIVATE);
            mCurrentIndex = mPreferences.getInt(ConstantManager.SAVED_INDEX, 0);
            score = mPreferences.getInt(ConstantManager.SAVED_SCORE, 0);
            mesResId = mPreferences.getInt(ConstantManager.SAVED_POINT, 0);
            answerIsTrue = mPreferences.getBoolean(ConstantManager.SAVED_ANSWER, false);
            mIsCheater = mPreferences.getBoolean(ConstantManager.SAVED_CHEATER, false);
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
            Log.i(ConstantManager.TAG, "boolean if cheat" + mIsCheater);
            if (answerIsTrue) {
                onAnswerTrue(mTrueButton);
                Log.i(ConstantManager.TAG, "boolean answer true");
            } else {
                onAnswerTrue(mFalseButton);
                Log.i(ConstantManager.TAG, "boolean answer false");
            }
        } else Log.i(ConstantManager.TAG, "boolean if mtrue" + mIsCheater);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mAnswerButton.setOnClickListener(this);
    }


    private void loadSavedInstanceState(Bundle savedInstanceState) {
        mFacts = FactLab.getInstance(this).getFacts();
        mCurrentIndex = savedInstanceState.getInt(ConstantManager.KEY_INDEX);
        score = savedInstanceState.getInt(ConstantManager.KEY_SCORE);
        mIsCheater = savedInstanceState.getBoolean(ConstantManager.KEY_CHEAT);
        answerIsTrue = savedInstanceState.getBoolean(ConstantManager.KEY_ANSWER);
        mesResId = savedInstanceState.getInt(ConstantManager.KEY_POINT);
        mCountFacts = mCurrentIndex + 1;
        tvCountFacts.setText(mCountFacts + " / " + mFacts.size());
        Log.i(ConstantManager.TAG, "loadInstate");
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
        mPreferences = getSharedPreferences(ConstantManager.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(ConstantManager.SAVED_INDEX, mCurrentIndex);
        editor.putInt(ConstantManager.SAVED_SCORE, score);
        editor.putInt(ConstantManager.SAVED_POINT, mesResId);
        editor.putBoolean(ConstantManager.SAVED_PASS, pass);
        editor.putBoolean(ConstantManager.SAVED_CHEATER, mIsCheater);
        editor.putBoolean(ConstantManager.SAVED_ANSWER, answerIsTrue);
        editor.apply();
        Log.i(ConstantManager.TAG, "saved pass " + pass);
        Log.i(ConstantManager.TAG, "saved DATA GAME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        FactLab.getInstance(this).saveFacts();
        Log.i(ConstantManager.TAG, "onPause");
    }
}

