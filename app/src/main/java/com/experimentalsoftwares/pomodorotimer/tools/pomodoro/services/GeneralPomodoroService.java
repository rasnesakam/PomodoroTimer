package com.experimentalsoftwares.pomodorotimer.tools.pomodoro.services;

import android.app.Activity;
import android.os.Handler;

import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnFinishListener;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnTimerChange;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.abstracts.AbstractTimer;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.timer.Timer;
import com.experimentalsoftwares.pomodorotimer.tools.utils.TimerQueue;

/**
 * <h1>
 *     General Pomodoro Service
 * </h1>
 *
 * <p>
 *     Manages and runs one or more pomodoros one by one
 * </p>
 *
 * @author Ensar Makas
 * @since 31.01.2021
 */
public abstract class GeneralPomodoroService extends AbstractTimer {
    private TimerQueue pomodoros;
    private Activity activity;
    private OnTimerChange onTimerChange;

    public GeneralPomodoroService(Activity activity, Handler handler) {
        super(handler);
        this.activity = activity;
        super.setPerSecond(this::pulse);
    }

    @Override
    public void start() {
        pomodoros.startQueue();
    }

    @Override
    public void pause() {
        pomodoros.getCurrentTimer().pause();
    }

    @Override
    public void resume() {
        pomodoros.getCurrentTimer().resume();
    }

    @Override
    public void reset() {
        pomodoros.getCurrentTimer().reset();
    }

    @Override
    public void setOnFinishListener(OnFinishListener onFinishListener) {
        pomodoros.onQueueFinished(onFinishListener);
    }

    // Abstract methods

    /**
     * <p>
     *     Action to execute when work timer executed
     * </p>
     */
    public abstract void onWork();

    /**
     * <p>
     *     Action to execute when rest timer executed
     * </p>
     */
    public abstract void onRest();

    /**
     * <p>
     *     Action to execute int timer per second
     * </p>
     * @param h hour
     * @param m minute
     * @param s second
     */
    public abstract void pulse(int h, int m, int s);

    // Getters and Setters

    public OnTimerChange getOnTimerChange() {
        return onTimerChange;
    }

    public void setOnTimerChange(OnTimerChange onTimerChange) {
        this.onTimerChange = onTimerChange;
    }


    // class methods

    /**
     * <p>
     *     Creates pomodoros and adds them each other
     * </p>
     * <p>
     *     Adds rest {@link Timer timer} between pomodoros
     * </p>
     * @param count how many pomodoros to create
     */
    public void preparePomodoros(int count){
        pomodoros = new TimerQueue(
                new SinglePomodoroService(activity,getHandler()) {
                    @Override
                    public void onRest() {
                        GeneralPomodoroService.this.onRest();
                    }

                    @Override
                    public void onWork() {
                        GeneralPomodoroService.this.onWork();
                    }

                    @Override
                    public void pulse(int h, int m, int s) {
                        GeneralPomodoroService.this.pulse(h,m,s);
                    }
                }
        );

        for (int i = 1; i < count; i++) {
            // Add rest timer before add new pomodoro
            pomodoros.bindElement(
                    Timer.rest(activity,getHandler(),this::pulse),
                    this::onRest
            );
            // Add new pomodoro
            pomodoros.bindElement(
                    new SinglePomodoroService(activity,getHandler()) {
                        @Override
                        public void onRest() {
                            GeneralPomodoroService.this.onRest();
                        }

                        @Override
                        public void onWork() {
                            GeneralPomodoroService.this.onWork();
                        }

                        @Override
                        public void pulse(int h, int m, int s) {
                            GeneralPomodoroService.this.pulse(h,m,s);
                        }
                    },
                    getOnTimerChange()
            );
        }

    }
}
