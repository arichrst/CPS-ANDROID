package com.choki.cps.Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Formatter {
    public static String ToJson(Object data)
    {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static <T> T FromJson(String data)
    {
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<T>(){}.getType());
    }

    public static <T> List<T> FromJsons(String data)
    {
        Gson gson = new Gson();
        try {
            List<T> result = new ArrayList<>();
            JSONArray array = new JSONArray(data);
            for (int i = 0 ; i < array.length() ; i++)
            {
                T item = FromJson(array.getString(i));
                result.add(item);
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
