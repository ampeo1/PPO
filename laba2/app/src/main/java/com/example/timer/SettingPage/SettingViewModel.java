package com.example.timer.SettingPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SettingViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> keyTheme = new MutableLiveData<>();
    public static final float startValue = 0.7f; //начальное значение размера шрифта
    public static final float step = 0.15f;

    int sizeCoef;


    public SettingViewModel(@NonNull Application application) {
        super(application);
    }

    public  boolean getKeyTheme() {
        if(keyTheme.getValue() == null){
            keyTheme.setValue(false);
        }
        return keyTheme.getValue();
    }
    public void setKeyTheme(boolean key){
        keyTheme.setValue(key);
    }
}
