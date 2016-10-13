package com.junior.dwan.geoquiz.utils;


import android.content.Context;

import com.junior.dwan.geoquiz.utils.Fact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Might on 27.07.2016.
 */
public class JSONSerializer {

    private Context mContext;
    private String mFilename;

    public JSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Fact> loadFacts() throws IOException, JSONException {
        ArrayList<Fact> facts = new ArrayList<Fact>();
        BufferedReader reader = null;
        try {
// Открытие и чтение файла в StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
// Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
// Разбор JSON с использованием JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
// Построение массива объектов Crime по данным JSONObject
            for (int i = 0; i < array.length(); i++) {
                facts.add(new Fact(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
// Происходит при начале "с нуля"; не обращайте внимания
        } finally {
            if (reader != null)
                reader.close();
        }
        return facts;
    }

    public void saveFacts(ArrayList<Fact> facts)
            throws JSONException, IOException {
// Построение массива в JSON
        JSONArray array = new JSONArray();
        for (Fact c : facts)
            array.put(c.toJSON());
// Запись файла на диск
        Writer writer = null;
        try {
            OutputStream out = mContext
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
