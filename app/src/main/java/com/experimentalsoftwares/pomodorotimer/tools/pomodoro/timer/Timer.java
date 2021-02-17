package com.experimentalsoftwares.pomodorotimer.tools.pomodoro.timer;

import android.app.Activity;
import android.os.Handler;

import com.experimentalsoftwares.pomodorotimer.tools.listeners.PerSecond;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.abstracts.AbstractTimer;
import com.experimentalsoftwares.pomodorotimer.tools.system.AppSettings;

/**
 * <h1>Timer</h1>
 *
 * <p>Executes timer operations</p>
 *
 * @author Ensar Makas
 * @since 29.01.2021
 */

public class Timer extends AbstractTimer implements Runnable {

    // Fields

    private int hours,minutes,seconds;
    private int[] backup;

    // Constructors

    /**
     * <p>Private Constructor of {@link Timer}</p>
     * @param hms stands for hour, minute, second
     * @param handler to manage timer
     * @param perSecond to take actions for every seconds
     */
    private Timer(
            int[] hms,
            Handler handler
            ,PerSecond perSecond
    ) {
        super(handler,perSecond);
        this.hours = hms[0];
        this.minutes = hms[1];
        this.seconds = hms[2];
        this.backup = hms;
    }



    /**
     * <p>
     *     Prepares Timer object to use for work timer
     * </p>
     * @param activity to access app settings for get Timer datas that user defined
     * @param handler to manage timer
     * @param perSecond to take actions in every second
     * @return new {@link Timer} object created for usage of work timing
     */
    public static Timer work(
            Activity activity,
            Handler handler,
            PerSecond perSecond
    ){

        AppSettings.PomodoroTimerSettings workSettings = new AppSettings.PomodoroTimerSettings.Work(activity);
        int[] hms = workSettings.getHMS();

        return new Timer(hms,handler,perSecond);
    }

    /**
     * <p>
     *     Prepares Timer object to use for resting timer
     * </p>
     * @param activity to access app settings for get Timer datas that user defined
     * @param handler to manage timer
     * @param perSecond to take actions in every second
     * @return new {@link Timer} object created for usage of rest timing
     */
    public static Timer rest(
            Activity activity,
            Handler handler,
            PerSecond perSecond
    ){
        AppSettings.PomodoroTimerSettings restSettings = new AppSettings.PomodoroTimerSettings.Rest(activity);
        int[] hms = restSettings.getHMS();

        return new Timer(hms,handler,perSecond);
    }

    // Implementations


    /**
     * <p>implementation of {@link Runnable}</p>
     * <p>
     *     It will decrease times and posts itself recursively after 1 second<br>
     *     Decrases 1 second per one second until all times (hours, minutes and seconds) are 0
     * </p>
     */
    @Override
    public void run() {

        if (reduceSecond()) {
            getPerSecond().doPerSecond(hours,minutes,seconds);
            getHandler().postDelayed(this,1000);
        }
        else if (getOnFinishListener() != null) getOnFinishListener().onFinish();

    }


    @Override
    public void start() {
        getHandler().post(this);
    }

    @Override
    public void pause() {
        getHandler().removeCallbacks(this);
    }

    @Override
    public void resume() {
        start();
    }

    @Override
    public void reset() {
        this.hours = backup[0];
        this.minutes = backup[1];
        this.seconds = backup[2];
    }


    // Class methods

    /**
     * <p>
     *     Checks if seconds is bigger than 0 and reduces seconds
     * </p>
     * @return true if seconds is bigger than 0. Else, returns {@link Timer::reduceMinute()}
     */
    public boolean reduceSecond(){
        if(seconds-- > 0) return true;
        else return reduceMinute();
    }

    /**
     * <p>
     *     Checks if minutes is bigger than 0, reduces minutes
     * </p>
     * <p>
     *     if it is true, reloads the seconds and returns true. <br>
     *     Else, returns {@link Timer::reduceHour()}
     * </p>
     *
     * @return if it is true, reloads the seconds and returns true. Else, returns {@link Timer::reduceHour()}
     */
    public boolean reduceMinute(){
        if (minutes-- > 0){
            seconds = 59;
            return true;
        }
        else return reduceHour();
    }

    /**
     * <p>
     *     Checks if minutes is bigger than 0, reduces minutes
     * </p>
     * <p>
     *     if it is true, reloads minutes and seconds, then returns true. <br>
     *     Else returns false
     * </p>
     *
     * @return if it is true, reloads minutes and seconds, then returns true. Else, returns false
     */
    public boolean reduceHour(){
        if (hours-- > 0){
            minutes = 59;
            seconds = 59;
            return true;
        }
        else return false;
    }

}
