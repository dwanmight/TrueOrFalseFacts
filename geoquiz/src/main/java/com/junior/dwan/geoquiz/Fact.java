package com.junior.dwan.geoquiz;

import java.util.UUID;

/**
 * Created by Might on 25.01.2016.
 */
public class Fact {
    private String mQuestion;
    private boolean mTrueQuestion;



    public Fact(String question, boolean trueQuestion){
        mQuestion=question;
        mTrueQuestion=trueQuestion;
    }


    public String getQuestion(){
        return mQuestion;
    }
    public void setQuestion(String question){
        mQuestion=question;
    }

    public boolean isTrueQuestion(){
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion){
        mTrueQuestion=trueQuestion;

    }
}
