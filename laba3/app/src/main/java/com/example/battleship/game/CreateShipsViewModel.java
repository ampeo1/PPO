package com.example.battleship.game;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.battleship.game.map.Field;
import com.example.battleship.game.map.CreateMapAdapter;
import com.example.battleship.model.Database;
import com.example.battleship.room.UserEnum;

import java.util.ArrayList;
import java.util.List;

public class CreateShipsViewModel extends AndroidViewModel {
    public CreateShipsViewModel(@NonNull Application application) {
        super(application);

    }

    public boolean pushMap(CreateMapAdapter adapter, UserEnum userEnum, String idRoom){
        if (adapter.checkCountShip()){
            Database.pushMap(userEnum, idRoom, adapter.getFields());
            return true;
        }
        else{
            return false;
        }
    }

    public List<Field> getEmptyMap(){
        ArrayList<Field> fields = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            fields.add(new Field(i));
        }

        return fields;
    }
}
