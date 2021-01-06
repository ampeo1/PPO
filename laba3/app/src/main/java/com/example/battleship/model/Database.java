package com.example.battleship.model;

import com.example.battleship.game.map.Field;
import com.example.battleship.room.UserEnum;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Database {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();

    public static void pushMap(UserEnum userEnum, String idRoom, List<Field> fields){
        DatabaseReference ref = db.getReference(idRoom
                + "/maps/" + userEnum.name());
        ref.setValue(fields);
        if (userEnum == UserEnum.FIRST_USER){
            getChangeTurnReference(idRoom).setValue(userEnum);
        }
    }

    public static DatabaseReference getMapReference(UserEnum userEnum, String idRoom){
        return db.getReference(idRoom + "/maps/" + userEnum.name());
    }

    public static DatabaseReference getChangeTurnReference(String idRoom){
        return db.getReference(idRoom + "/maps/turn");
    }

    public static DatabaseReference getStatisticCurrentReference(){
        return db.getReference("statistics/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public static DatabaseReference getStatusBattle(String idRoom){
        return db.getReference(idRoom);
    }

    public static DatabaseReference getCurrentUserRoomRef(String idRoom, UserEnum userEnum){
        return db.getReference(idRoom).child(userEnum.name());
    }

    public static DatabaseReference getCurrentUserAvatar(String idRoom, UserEnum userEnum){
        return getCurrentUserRoomRef(idRoom, userEnum).child("avatar");
    }

    public static DatabaseReference getCurrentUsername(String idRoom, UserEnum userEnum){
        return getCurrentUserRoomRef(idRoom, userEnum).child("username");
    }

    public static DatabaseReference getCurrentUserStatus(String idRoom, UserEnum userEnum){
        return getCurrentUserRoomRef(idRoom, userEnum).child("status");
    }

    public static DatabaseReference getRoom(String code){
        return db.getReference(code);
    }
}
