package com.experimentalsoftwares.pomodorotimer.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.experimentalsoftwares.pomodorotimer.tools.system.AppSettings;
public class NotifySound {
    private Activity activity;
    private int resId;

    public NotifySound(Activity activity, int resId) {
        this.activity = activity;
        this.resId = resId;
    }

    public void play(){
        MediaPlayer player = MediaPlayer.create(activity,resId);
        player.start();
        player.setOnCompletionListener((mediaPlayer)->{
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        });
    }

    // Sub Classes

    public static class Work extends NotifySound{

        public Work(Activity activity) {
            super(
                    activity,
                    new AppSettings.NotificationSound.Work(activity).getSound()
            );
        }
    }

    public static class Rest extends NotifySound{
        public Rest(Activity activity) {
            super(
                    activity,
                    new AppSettings.NotificationSound.Rest(activity).getSound()
            );
        }
    }
}
