package com.experimentalsoftwares.pomodorotimer.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;

public class Vibrator {
    public static void vibrate(Activity activity){
        android.os.Vibrator vibrator = (android.os.Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        long[] pattern = {0,500,250,500};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK));
        else vibrator.vibrate(pattern, -1);



    }
}
