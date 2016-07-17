package com.junior.dwan.fortest;

import java.util.ArrayList;

/**
 * Created by Might on 17.07.2016.
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        for (int i = 0; i <10 ; i++) {
            list.add("obj"+i);

        }
        System.out.println(list.get(3));
    }
}
