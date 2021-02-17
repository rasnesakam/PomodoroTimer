package com.experimentalsoftwares.pomodorotimer.tools.system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.experimentalsoftwares.pomodorotimer.R;

public abstract class AppSettings {
    private final SharedPreferences prefs;

    public AppSettings(Activity activity) {
        this.prefs = activity.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }


    /**
     * Inner class that gets and sets pomodoro timing
     *
     * @author Ensar Makas
     * @since 06.02.2021
     */
    public static class PomodoroTimerSettings extends AppSettings{
        public Activity activity;
        private final String hour;
        private final String min;
        private final String sec;
        private int[] defaultHMS;

        public PomodoroTimerSettings(Activity activity, String hour, String min, String sec,int[] defaultHMS) {
            super(activity);
            this.activity = activity;
            this.hour = hour;
            this.min = min;
            this.sec = sec;
            this.defaultHMS = defaultHMS;
        }

        public void setHMS(int[] hms){
            SharedPreferences.Editor editor = super.prefs.edit();
            editor.putInt(hour,hms[0]);
            editor.putInt(min,hms[1]);
            editor.putInt(sec,hms[2]);
            editor.apply();
        }
        public int[] getHMS(){
            return new int[] {
                    super.prefs.getInt(hour,defaultHMS[0]),
                    super.prefs.getInt(min,defaultHMS[1]),
                    super.prefs.getInt(sec,defaultHMS[2])
            };
        }



        public static class Work extends PomodoroTimerSettings{

            public Work(Activity activity) {
                super(
                        activity,
                        "pomodoroWorkHour",
                        "pomodoroWorkMinute",
                        "pomodoroWorkSecond",
                        new int[] {0,0,10} // default hms
                );
            }
        }

        public static class Rest extends PomodoroTimerSettings{

            public Rest(Activity activity) {
                super(
                        activity,
                        "pomodoroRestHour",
                        "pomodoroRestMinute",
                        "pomodoroRestSecond",
                        new int[]{0,0,5} // default hms
                );
            }
        }

    }

    /**
     * Inner class that gets and sets app theme
     *
     * @author Ensar Makas
     * @since 06.02.2021
     */
    public static class AppThemeSetting extends AppSettings{
        private static final int defaultTheme = R.style.Theme_PomodoroTimer;
        private static final String themeKey = "appTheme";

        public AppThemeSetting(Activity activity) {
            super(activity);
        }

        public void setTheme(int resId){
            super.prefs.edit().putInt(themeKey,resId).apply();
        }

        public int getTheme() {
            return super.prefs.getInt(themeKey,defaultTheme);
        }

    }

    /**
     * Inner class that gets and sets pomodoro notification sound
     *
     * @author Ensar Makas
     * @since 06.02.2021
     */
    public static class NotificationSound extends AppSettings{

        private String key;
        private int defaultSound;

        public NotificationSound(Activity activity,String key,int defaultSound) {
            super(activity);
            this.key = key;
            this.defaultSound = defaultSound;
        }

        public void setSound(int resId){
            super.prefs.edit().putInt(key,resId).apply();
        }

        public int getSound(){
            return super.prefs.getInt(key,defaultSound);
        }

        // Sub classes


        /**
         * Inner class that gets and sets pomodoro notification sound for working status
         *
         * @author Ensar Makas
         * @since 06.02.2021
         */
        public static class Work extends NotificationSound{
            public Work(Activity activity) {
                super(
                        activity,
                        "notificationSoundWork",
                        R.raw.default_ringtone
                );
            }
        }

        /**
         * Inner class that gets and sets pomodoro notification sound for resting status
         *
         * @author Ensar Makas
         * @since 06.02.2021
         */
        public static class Rest extends NotificationSound {
            public Rest(Activity activity) {
                super(
                        activity,
                        "notificationSoundRest",
                        R.raw.default_ringtone
                );
            }
        }

    }

}
