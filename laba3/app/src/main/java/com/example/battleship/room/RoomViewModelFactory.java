package com.example.battleship.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RoomViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private String idRoom;

    public RoomViewModelFactory(Application application, String idRoom){
        this.application = application;
        this.idRoom = idRoom;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RoomViewModel(application,idRoom);
    }
}
