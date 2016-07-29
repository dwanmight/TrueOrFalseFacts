package com.junior.dwan.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Might on 14.07.2016.
 */
public class FirstActivity extends Activity implements View.OnClickListener {
    private Button btnNewGame, btnContinueGame,btnReset;
    public static final String EXTRA_START_GAME="com.junior.dwan.geoquiz.start_game";
    private int sHighScore=0;
    private int sStartGame ;
    public static final String SAVED_HScore="hscore";
    private TextView tvHighScore;
    private boolean pass;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnNewGame=(Button)findViewById(R.id.btnNewGame);
        btnContinueGame =(Button)findViewById(R.id.btnContinueGame);
        btnReset=(Button)findViewById(R.id.btnReset);

        mPreferences=getSharedPreferences(GameActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        if(mPreferences.contains(GameActivity.SAVED_PASS)) pass=mPreferences.getBoolean(GameActivity.SAVED_PASS,false);
        if(pass){
            btnContinueGame.setEnabled(true);
        }   else btnContinueGame.setEnabled(false);

        tvHighScore=(TextView)findViewById(R.id.tvHighScore);
        sHighScore=getSharedPreferences("sPref",MODE_PRIVATE).getInt(SAVED_HScore,0);
        if(getIntent().getIntExtra(PassActivity.EXTRA_HIGHSCORE, 0)>sHighScore){
            sHighScore= getIntent().getIntExtra(PassActivity.EXTRA_HIGHSCORE, 0);
        }
        tvHighScore.setText(getString(R.string.highscore) + sHighScore);

        btnContinueGame.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btnContinueGame:
                sStartGame=1;
                i=new Intent(FirstActivity.this,GameActivity.class);
                i.putExtra(EXTRA_START_GAME,sStartGame);
                startActivity(i);
                break;
            case R.id.btnNewGame:
                sStartGame =0;
                i=new Intent(FirstActivity.this,GameActivity.class);
                i.putExtra(EXTRA_START_GAME, sStartGame);
                startActivity(i);
                break;
            case R.id.btnReset:
                sHighScore = 0;
                tvHighScore.setText(getString(R.string.highscore) + sHighScore);
                pass=false;
                btnContinueGame.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPreferences=getSharedPreferences(GameActivity.SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putInt(SAVED_HScore, sHighScore);
        ed.putBoolean(GameActivity.SAVED_PASS, pass);
        ed.apply();
    }
}
