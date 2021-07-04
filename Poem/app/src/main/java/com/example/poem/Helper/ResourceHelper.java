package com.example.poem.Helper;

import android.content.Context;
import android.content.res.TypedArray;
import com.example.poem.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {

    public static List<String[]> get2DResArray(Context context, String key) {
        List<String[]> array = new ArrayList<String[]>();

        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;

            do {
                field = res.getField(key + "_" + counter);
                counter++;
                array.add(context.getResources().getStringArray(field.getInt(null)));
            } while (field != null);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return array;
    }
}
