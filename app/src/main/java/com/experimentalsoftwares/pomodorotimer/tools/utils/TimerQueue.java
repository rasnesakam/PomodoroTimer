
package com.experimentalsoftwares.pomodorotimer.tools.utils;

import android.util.Log;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnFinishListener;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnStartListener;
import com.experimentalsoftwares.pomodorotimer.tools.listeners.OnTimerChange;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.abstracts.AbstractTimer;

public class TimerQueue {
    private AbstractTimer first,last,current;
    private OnStartListener listener;

    public TimerQueue(AbstractTimer first){
        this.first = first;
        last = first;
        current = first;
    }


    public void bindElement(AbstractTimer next, OnTimerChange onTimerChange){
        last.setOnFinishListener(()->{
            Log.i("Changed!!", "bindElement: timer changed");
            next.start();
            if (onTimerChange != null) onTimerChange.onChanged();
            current = next;
        });
        last = next;
    }

    public void startQueue(){
        if (listener != null) listener.onStart();
        first.start();
    }

    public void onQueueFinished(OnFinishListener listener){
        last.setOnFinishListener(listener);
    }

    public void onQueueStarted(OnStartListener listener){
        this.listener = listener;
    }

    public AbstractTimer getCurrentTimer(){
        return current;
    }

}
