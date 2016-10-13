package com.junior.dwan.geoquiz.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.junior.dwan.geoquiz.R;
import com.junior.dwan.geoquiz.utils.ConstantManager;

/**
 * Created by Might on 14.07.2016.
 */
public class FirstActivity extends Activity implements View.OnClickListener {
    private Button btnNewGame, btnContinueGame, btnReset;
    private int sHighScore = 0;
    private int sStartGame;
    private TextView tvHighScore;
    private boolean pass;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        initializeMainView();

        mPreferences = getSharedPreferences(ConstantManager.SHARED_PREFERENCES, MODE_PRIVATE);
        if (mPreferences.contains(ConstantManager.SAVED_PASS))
            pass = mPreferences.getBoolean(ConstantManager.SAVED_PASS, false);
        if (pass) {
            btnContinueGame.setEnabled(true);
        } else btnContinueGame.setEnabled(false);

        sHighScore = getSharedPreferences("sPref", MODE_PRIVATE).getInt(ConstantManager.SAVED_HScore, 0);
        if (getIntent().getIntExtra(ConstantManager.EXTRA_HIGHSCORE, 0) > sHighScore) {
            sHighScore = getIntent().getIntExtra(ConstantManager.EXTRA_HIGHSCORE, 0);
        }
        tvHighScore.setText(getString(R.string.highscore) + sHighScore);
        setClickListener();
    }

    private void initializeMainView() {
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnContinueGame = (Button) findViewById(R.id.btnContinueGame);
        btnReset = (Button) findViewById(R.id.btnReset);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnContinueGame:
                sStartGame = 1;
                i = new Intent(FirstActivity.this, GameActivity.class);
                i.putExtra(ConstantManager.EXTRA_START_GAME, sStartGame);
                startActivity(i);
                break;
            case R.id.btnNewGame:
                sStartGame = 0;
                i = new Intent(FirstActivity.this, GameActivity.class);
                i.putExtra(ConstantManager.EXTRA_START_GAME, sStartGame);
                startActivity(i);
                break;
            case R.id.btnReset:
                // вызываем диалог
                showDialog(ConstantManager.DIALOG_RESET);
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == ConstantManager.DIALOG_RESET) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.score_title);
            adb.setMessage(R.string.score_text);
            adb.setPositiveButton(R.string.score_ok, DialogListener);
            adb.setNegativeButton(R.string.score_no, DialogListener);
            adb.setNeutralButton(R.string.score_cancel, DialogListener);
            adb.setIcon(android.R.drawable.ic_dialog_info);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener DialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    resetScore();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    dialog.dismiss();
                case Dialog.BUTTON_NEUTRAL:
                    dialog.cancel();
            }
        }
    };

    private void setClickListener() {
        btnContinueGame.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void resetScore() {
        sHighScore = 0;
        tvHighScore.setText(getString(R.string.highscore) + sHighScore);
        pass = false;
        btnContinueGame.setEnabled(false);
    }

    private void savetoPreferences() {
        mPreferences = getSharedPreferences(ConstantManager.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putInt(ConstantManager.SAVED_HScore, sHighScore);
        ed.putBoolean(ConstantManager.SAVED_PASS, pass);
        ed.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savetoPreferences();
    }
}
