package com.example.poem.activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class ActivityController {
    /**
     * Get current activity.
     *
     * @param context Current context.
     * @return Current activity who provide the context.
     */
    public static Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else {
            return null;
        }
    }
}
