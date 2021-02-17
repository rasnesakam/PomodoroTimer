package com.experimentalsoftwares.pomodorotimer.tools.pomodoro.services;

import android.app.Activity;
import android.os.Handler;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnFinishListener;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.PerSecond;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.abstracts.AbstractTimer;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.timer.Timer;
import com.experimentalsoftwares.pomodorotimer.tools.utils.TimerQueue;


/**
 * <h1>Single Pomodoro Service</h1>
 * <p>
 *     Creates pomodoro timers by managing timers
 * </p>
 * <p>
 *     Creates 3 {@link Timer timers} and executes them one by one
 * </p>
 * @author Ensar Makas
 * @since 31.01.2021
 */
public abstract class SinglePomodoroService extends AbstractTimer {
    // Fields
    private TimerQueue queue;

    public SinglePomodoroService(
            Activity activity,
            Handler handler
    ){
        super(handler);
        super.setPerSecond(this::pulse);

        Timer workF = Timer.work(activity,handler,this::pulse);
        Timer rest = Timer.rest(activity,handler,this::pulse);
        Timer workL = Timer.work(activity,handler,this::pulse);

        queue = new TimerQueue(workF);
        queue.bindElement(rest,this::onRest);
        queue.bindElement(workL,this::onWork);

        queue.onQueueStarted(this::onWork);

    }

    @Override
    public void start() {
        queue.startQueue();
    }

    @Override
    public void pause() {
        queue.getCurrentTimer().pause();
    }

    @Override
    public void resume() {
        queue.getCurrentTimer().resume();
    }

    @Override
    public void reset() {
        queue.getCurrentTimer().reset();
    }

    @Override
    public void setOnFinishListener(OnFinishListener onFinishListener) {
        queue.onQueueFinished(onFinishListener);
    }

    // Abstracts

    public abstract void onRest();

    public abstract void onWork();

    public abstract void pulse(int h, int m, int s);



}
