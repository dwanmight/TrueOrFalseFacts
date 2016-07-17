package com.junior.dwan.geoquiz;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Might on 17.07.2016.
 */
public class FactLab {
    private static FactLab sFactLab ;
    private ArrayList<Fact> mFacts;
    private Context mAppContext;
    private String[] factArray;
    private Boolean[] factBoolean;



    private FactLab(Context appContext) {
        TypedArray ar = mAppContext.getResources().obtainTypedArray(R.array.factsArray);
        int len = ar.length();
        int[] factsQuests = new int[len];
        for (int i = 0; i < len; i++)
            factsQuests[i] = ar.getInteger(i,0);
        ar.recycle();

        TypedArray bo = mAppContext.getResources().obtainTypedArray(R.array.factsBoolean);
        int lenBo = bo.length();
        Boolean[] factsBoolean = new Boolean[lenBo];
        for (int i = 0; i < lenBo; i++)
            factsBoolean[i]=bo.getBoolean(i,false);
        bo.recycle();

        mFacts=new ArrayList<Fact>();
        for (int i = 0; i <=5; i++) {
           Fact f=new Fact(factsQuests[i],factsBoolean[i]);
            mFacts.add(f);
        }
    }

    public static FactLab getInstance(Context c) {
        if(sFactLab==null) {
            sFactLab=new FactLab(c.getApplicationContext());

        }
        return sFactLab;
    }

//    public Fact getFact(UUID id){
//        for(Fact f:mFacts){
//            if(f.getId().equals(id))
//                return f;
//
//        }
//        return null;
//    }

    public ArrayList<Fact> getFacts() {
        return mFacts;
    }


}
