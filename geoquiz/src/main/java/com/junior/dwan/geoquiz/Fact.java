package com.junior.dwan.geoquiz;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Might on 25.01.2016.
 */
public class Fact {
    public static final String JSON_QUESTION="question";
    public static final String JSON_ISTRUE="istrue";
    private String mQuestion;
    private boolean mTrueQuestion;

    public Fact(String question, boolean trueQuestion){
        mQuestion=question;
        mTrueQuestion=trueQuestion;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_QUESTION, mQuestion);
        json.put(JSON_ISTRUE, mTrueQuestion);
        return json;
    }

    public Fact(JSONObject json) throws JSONException {
       mQuestion = json.getString(JSON_QUESTION);
        mTrueQuestion = json.getBoolean(JSON_ISTRUE);
    }

    public String getQuestion(){
        return mQuestion;
    }

    public boolean isTrueQuestion(){
        return mTrueQuestion;
    }
}
