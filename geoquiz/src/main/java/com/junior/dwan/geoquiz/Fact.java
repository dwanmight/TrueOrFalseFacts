package com.junior.dwan.geoquiz;

import java.util.UUID;

/**
 * Created by Might on 25.01.2016.
 */
public class Fact {
    private int mQuestion;
    private boolean mTrueQuestion;



    public Fact(int question, boolean trueQuestion){
        mQuestion=question;
        mTrueQuestion=trueQuestion;
    }


    public int getQuestion(){
        return mQuestion;
    }
    public void setQuestion(int question){
        mQuestion=question;
    }

    public boolean isTrueQuestion(){
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion){
        mTrueQuestion=trueQuestion;

    }
}
