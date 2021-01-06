package com.example.battleship.game;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.battleship.game.map.Field;
import com.example.battleship.model.Database;
import com.example.battleship.room.UserEnum;
import com.example.battleship.statistics.Statistic;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private String idRoom;
    private UserEnum user;
    private UserEnum enemyUser;
    private ArrayList<Field> enemyMap;
    private MutableLiveData<Boolean> changeMap;
    private MutableLiveData<Boolean> turn;
    private MutableLiveData<Boolean> statusGame;

    public MutableLiveData<Boolean> getStatusGame() {
        if(statusGame == null){
            statusGame = new MutableLiveData<>();
            statusGame.setValue(true);
        }
        return statusGame;
    }

    public MutableLiveData<Boolean> getTurn() {
        if (turn == null){
            turn = new MutableLiveData<>();
            turn.setValue(false);
        }
        return turn;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public UserEnum getUserEnum() {
        return user;
    }

    public MutableLiveData<Boolean> getChangeMapLiveData(){
        if(changeMap == null){
            changeMap = new MutableLiveData<>();
            changeMap.setValue(false);
        }

        return changeMap;
    }

    public GameViewModel(@NonNull Application application, String idRoom, String userEnum) {
        super(application);
        this.idRoom = idRoom;
        this.user = UserEnum.valueOf(userEnum);
        if(user == UserEnum.FIRST_USER){
            enemyUser = UserEnum.SECOND_USER;
        }
        else{
            enemyUser = UserEnum.FIRST_USER;
        }

        DatabaseReference ref = Database.getMapReference(enemyUser, getIdRoom());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("data", "зашёл");
                Field field = snapshot.getValue(Field.class);
                Log.e("new data", field.getStatus().name());
                getEnemyMap().add(field);
                if(getEnemyMap().size() == 100){
                    Log.e("enemyMap", "100");
                    getChangeMapLiveData().setValue(true);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database.getChangeTurnReference(idRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(UserEnum.class) == user){
                    getTurn().setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database.getStatusBattle(idRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                if(result != null && result.equals("finish")){
                    Database.getStatisticCurrentReference().push().setValue(new Statistic(getIdRoom(), "Поражение"));
                    getStatusGame().setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void finishGame(){
        Database.getStatisticCurrentReference().push().setValue(new Statistic(getIdRoom(), "Победа"));
        getStatusGame().setValue(false);
    }

    public List<Field> getEnemyMap() {
        if(enemyMap == null){
            enemyMap = new ArrayList<>();
        }

        return enemyMap;
    }

    public void changeTurn(){
        Database.getChangeTurnReference(idRoom).setValue(enemyUser);
    }
}
