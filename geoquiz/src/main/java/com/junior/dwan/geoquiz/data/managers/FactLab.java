package com.junior.dwan.geoquiz.data.managers;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.junior.dwan.geoquiz.utils.Fact;
import com.junior.dwan.geoquiz.R;
import com.junior.dwan.geoquiz.utils.JSONSerializer;

import java.util.ArrayList;

/**
 * Created by Might on 17.07.2016.
 */
public class FactLab {
    public static final String FILENAME = "facts.json";
    public static final String TAG = "tag";
    private ArrayList<Fact> mFacts;
    private JSONSerializer mSerializer;
    private static FactLab sFactLab;
    private Context mAppContext;

    private FactLab(Context appContext) {

        mAppContext = appContext;
        mSerializer = new JSONSerializer(mAppContext, FILENAME);
        try {
            mFacts = mSerializer.loadFacts();
            Log.i(TAG, mFacts.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static FactLab getInstance(Context c) {
        if (sFactLab == null) {
            sFactLab = new FactLab(c.getApplicationContext());
        }
        return sFactLab;
    }

    public ArrayList<Fact> loadNewFact(Context appContext) {
        mFacts.clear();
        //load array-questions
        String[] factArray = appContext.getResources().getStringArray(R.array.factsArray);
        //load array boolean
        TypedArray bo = appContext.getResources().obtainTypedArray(R.array.factsBoolean);
        int lenBo = bo.length();
        Boolean[] factsBoolean = new Boolean[lenBo];
        for (int i = 0; i < lenBo; i++)
            factsBoolean[i] = bo.getBoolean(i, false);
        bo.recycle();
        //add questions and boolean values to List
        for (int i = 0; i < factArray.length; i++) {
            Fact f = new Fact(factArray[i], factsBoolean[i]);
            mFacts.add(f);
        }
        return mFacts;
    }

    public ArrayList<Fact> getFacts() {
        return mFacts;
    }

    public boolean saveFacts() {
        try {
            mSerializer.saveFacts(mFacts);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }


}
