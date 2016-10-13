package com.junior.dwan.geoquiz.utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.junior.dwan.geoquiz.utils.ConstantManager.JSON_ISTRUE;
import static com.junior.dwan.geoquiz.utils.ConstantManager.JSON_QUESTION;

/**
 * Created by Might on 25.01.2016.
 */
public class Fact {
    private String mQuestion;
    private boolean mTrueQuestion;

    public Fact(String question, boolean trueQuestion) {
        mQuestion = question;
        mTrueQuestion = trueQuestion;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ConstantManager.JSON_QUESTION, mQuestion);
        json.put(ConstantManager.JSON_ISTRUE, mTrueQuestion);
        return json;
    }

    public Fact(JSONObject json) throws JSONException {
        mQuestion = json.getString(ConstantManager.JSON_QUESTION);
        mTrueQuestion = json.getBoolean(ConstantManager.JSON_ISTRUE);
    }

    public String getQuestion() {
        return mQuestion;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }
}
