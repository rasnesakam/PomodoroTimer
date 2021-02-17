package com.experimentalsoftwares.pomodorotimer.tools.system.ui.abstracts;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;

public abstract class SettingForm {

    public void getDialog(Activity activity){
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(getForm(activity));
        dialog.show();
    }

    public abstract View getForm(Activity activity);


}
