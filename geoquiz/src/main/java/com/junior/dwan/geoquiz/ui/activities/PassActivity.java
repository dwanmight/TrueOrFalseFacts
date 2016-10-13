package com.junior.dwan.geoquiz.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.junior.dwan.geoquiz.R;
import com.junior.dwan.geoquiz.ui.activities.FirstActivity;
import com.junior.dwan.geoquiz.utils.ConstantManager;

/**
 * Created by Might on 22.07.2016.
 */
public class PassActivity extends Activity {
    TextView tvHead, tvMain;
    Button btnReturnMenu;
    private int score;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        tvHead = (TextView) findViewById(R.id.tvPassHead);
        tvMain = (TextView) findViewById(R.id.tvPassMain);
        btnReturnMenu = (Button) findViewById(R.id.btnReturnMenu);

        Intent i = getIntent();
        score = i.getIntExtra(ConstantManager.EXTRA_SCORE, 0);
        if (score >= 0 && score <= 40) {
            tvHead.setText(R.string.pass_not_bad);
            tvMain.setText((getString(R.string.pass_have)) + score + getString(R.string.pass_not_bad_main));

        } else if (score > 40 && score <= 80) {
            tvHead.setText(R.string.pass_good);
            tvMain.setText((getString(R.string.pass_have)) + score + getString(R.string.pass_good_main));
        } else {
            tvHead.setText(R.string.pass_excellent);
            tvMain.setText((getString(R.string.pass_have)) + score + getString(R.string.pass_excellent_main));
        }


    }

    public void onClickReturn(View v) {
        i = new Intent(this, FirstActivity.class);
        i.putExtra(ConstantManager.EXTRA_HIGHSCORE, score);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
