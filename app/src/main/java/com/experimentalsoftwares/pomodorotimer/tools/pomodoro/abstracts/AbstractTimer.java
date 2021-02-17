package com.experimentalsoftwares.pomodorotimer.tools.pomodoro.abstracts;

import android.os.Handler;

import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnFinishListener;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.PerSecond;

/**
 *
 * <h1>Abstract Timer</h1>
 * <p>Abstract class for Timers</p><br>
 * <h3>Sub classes</h3>
 * <ul>
 *     <li>
 *         {@link com.experimentalsoftwares.pomodorotimer.tools.pomodoro.timer.Timer Timer}
 *     </li>
 *     <li>
 *         {@link com.experimentalsoftwares.pomodorotimer.tools.pomodoro.services.SinglePomodoroService Single Pomodoro Service}
 *     </li>
 *     <li>
 *         {@link com.experimentalsoftwares.pomodorotimer.tools.pomodoro.services.GeneralPomodoroService General Pomodoro Service}
 *     </li>
 * </ul>
 * @author Ensar Makas
 * @version 1.0
 * @since 31.01.2021
 */

public abstract class AbstractTimer {
    private Handler handler;
    private OnFinishListener onFinishListener;
    private PerSecond perSecond;

    public AbstractTimer(
            Handler handler,
            PerSecond perSecond
    ){
        this.handler = handler;
        this.perSecond = perSecond;
    }

    public AbstractTimer(Handler handler){
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    public OnFinishListener getOnFinishListener() {
        return onFinishListener;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public PerSecond getPerSecond() {
        return perSecond;
    }

    public void setPerSecond(PerSecond perSecond) {
        this.perSecond = perSecond;
    }

    public abstract void start();
    public abstract void pause();
    public abstract void resume();
    public abstract void reset();
}
