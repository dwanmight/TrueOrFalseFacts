package com.junior.dwan.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Might on 14.07.2016.
 */
public class FirstActivity extends Activity implements View.OnClickListener {
    private Button btnNewGame,btnContinuegame;
    public static final String EXTRA_START_GAME="com.junior.dwan.geoquiz.start_game";
    public static int startGame = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnNewGame=(Button)findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(this);

        btnContinuegame=(Button)findViewById(R.id.btnContinueGame);
        btnContinuegame.setEnabled(false);
        btnContinuegame.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btnContinueGame:
                // if(startGame>2)
                // startGame=???????0
                i=new Intent(FirstActivity.this,GameActivity.class);
                startActivity(i);
                break;
            case R.id.btnNewGame:
                startGame=-1;
                i=new Intent(FirstActivity.this,GameActivity.class);
                i.putExtra(EXTRA_START_GAME,startGame);
                startActivity(i);
                break;
            default:
                break;

        }
    }
}
