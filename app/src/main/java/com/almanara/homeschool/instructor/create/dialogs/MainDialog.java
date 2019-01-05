package com.almanara.homeschool.instructor.create.dialogs;

import android.app.Activity;

import com.almanara.homeschool.instructor.create.OnEditLayoutReady;
import com.almanara.homeschool.instructor.create.OnLayoutReadyInterface;
import com.almanara.homeschool.instructor.create.ProgressImage;

/**
 * Created by Ali on 6/18/2017.
 */

public abstract class MainDialog {
    Integer id;
    Activity activity;
    boolean isEditing = false;
    int index;
    OnEditLayoutReady onEditLayoutReady;
    OnLayoutReadyInterface onLayoutReadyInterface;
    ProgressImage progressImage;
    public void setProgressImage(
            ProgressImage progressImage) {
        this.progressImage = progressImage;
    }
    public MainDialog(
            OnLayoutReadyInterface onLayoutReadyInterface) {
        this.onLayoutReadyInterface = onLayoutReadyInterface;
    }

    public MainDialog(Integer id, Activity activity,
                      OnLayoutReadyInterface onLayoutReadyInterface) {
        this.id = id;
        this.activity = activity;
        this.onLayoutReadyInterface = onLayoutReadyInterface;
    }

    public void setOnEditLayoutReady(
            OnEditLayoutReady onEditLayoutReady) {
        this.onEditLayoutReady = onEditLayoutReady;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}
