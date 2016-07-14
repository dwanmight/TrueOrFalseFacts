package com.junior.dwan.geoquiz;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;


    private TextView mQuestionTextView;
    private boolean mIsCheater;
    private static final String KEY_INDEX="index";
    private static final String KEY_INTENT_DATA="intentData";




    private TrueFalse[] mQuestionBank=new TrueFalse[]{
            new TrueFalse(R.string.question_oceans,true),
            new TrueFalse(R.string.question_mideast,false),
            new TrueFalse(R.string.question_africa,false),
            new TrueFalse(R.string.question_americas,true),
            new TrueFalse(R.string.question_asia,true)
    };

//    private int [] mCheatQuestions=new int[mQuestionBank.length];

//    private void findCheater(int cheaterQuestion){
//        if(mIsCheater){
//            mCheatQuestions[]
//        }
//    }

    private int mCurrentIndex=0;

    private void updateQuestion(){


        int question=mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isTrueQuestion();

        int mesResId=0;
        if(mIsCheater){
            if(userPressedTrue==answerIsTrue){mesResId=R.string.judgment_toast;}
            else{ mesResId=R.string.incorrect_judgement_toast;}
        }
        else{
         if(userPressedTrue==answerIsTrue)
             mesResId=R.string.correct_toast;
        else {mesResId=R.string.incorrect_toast;}}


        Toast.makeText(this,mesResId,Toast.LENGTH_SHORT).show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);


        mIsCheater=false;

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                checkAnswer(true);

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton=(Button)findViewById(R.id.cheatButton);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(GameActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });


        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                if(mIsCheater){
//
//                }
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                mIsCheater=false;
                updateQuestion();
            }
        });
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater=savedInstanceState.getBoolean(KEY_INTENT_DATA,false);
        }
        updateQuestion();
    }
    public void onStart(){
        super.onStart();

    }

    public void onPause(){
        super.onPause();

    }

    public void onResume(){
        super.onResume();

    }

    public void onStop(){
        super.onStop();

    }

    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean(KEY_INTENT_DATA,mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null) return;
        mIsCheater=data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.activity_menu,menu);
//        return true;
//    }
}
