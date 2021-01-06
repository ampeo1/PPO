package com.example.battleship.game;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GameViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private String idRoom;
    private String userEnum;

    public GameViewModelFactory(Application application, String idRoom, String userEnum){
        this.application = application;
        this.idRoom = idRoom;
        this.userEnum = userEnum;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameViewModel(application,idRoom, userEnum);
    }
}
