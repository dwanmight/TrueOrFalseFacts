package com.junior.dwan.geoquiz;

import android.content.Context;
import android.content.res.TypedArray;
import java.util.ArrayList;

/**
 * Created by Might on 17.07.2016.
 */
public class FactLab {
    private static FactLab sFactLab ;
    private ArrayList<Fact> mFacts;
    private Context mAppContext;
    private String[] factArray;
    private Boolean[] factsBoolean;

private FactLab(Context appContext) {
        factArray=appContext.getResources().getStringArray(R.array.factsArray);

        TypedArray bo = appContext.getResources().obtainTypedArray(R.array.factsBoolean);
        int lenBo = bo.length();
        factsBoolean = new Boolean[lenBo];
        for (int i = 0; i < lenBo; i++)
            factsBoolean[i]=bo.getBoolean(i,false);
        bo.recycle();

        mFacts=new ArrayList<Fact>();
        for (int i = 0; i <factArray.length; i++) {
           Fact f=new Fact(factArray[i],factsBoolean[i]);
            mFacts.add(f);
        }
    }

    public static FactLab getInstance(Context c) {
        if(sFactLab==null) {
            sFactLab=new FactLab(c.getApplicationContext());
        }
        return sFactLab;
    }

    public ArrayList<Fact> getFacts() {
        return mFacts;
    }
}
