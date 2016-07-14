package com.junior.dwan.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Might on 14.07.2016.
 */
public class FirstActivity extends Activity {
    private Button btnNewGame,btnContinuegame;
    public static final String EXTRA_START_GAME="com.junior.dwan.geoquiz.start_game";
    public static int startGame = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnNewGame=(Button)findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame=-1;
                Intent i=new Intent(FirstActivity.this,GameActivity.class);
                i.putExtra(EXTRA_START_GAME,startGame);
                startActivity(i);
            }
        });

        btnContinuegame=(Button)findViewById(R.id.btnContinueGame);
        btnContinuegame.setEnabled(false);
        btnContinuegame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
// if(startGame>2)
// startGame=???????
                Intent i=new Intent(FirstActivity.this,GameActivity.class);
                startActivity(i);
            }
        });



    }
}
