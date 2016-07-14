package com.junior.dwan.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Might on 26.02.2016.
 */
public class CheatActivity extends Activity {
    private Button mShowAnswer;
    private TextView mAnswerTextView;
    private boolean mIsCheater;
    private TextView mTvApiLvl;

    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.junior.dwan.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN ="com.junior.dwan.geoquiz.answer_shown";

    private static final String KEY_IS_ANSWER_SHOWN="answerShown";

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);

    }
    private boolean mAnswerIsTrue;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        setAnswerShownResult(false);

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView=(TextView)findViewById(R.id.answerTextView);
        mShowAnswer=(Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);}
                mIsCheater=true;
                setAnswerShownResult(true);
            }
        });
        if(savedInstanceState!=null){
            mIsCheater=savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN, false);
            setAnswerShownResult(true);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_ANSWER_SHOWN,mIsCheater);

    }
}
