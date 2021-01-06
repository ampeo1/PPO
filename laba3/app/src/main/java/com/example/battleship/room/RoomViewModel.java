package com.example.battleship.room;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.battleship.model.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;

public class RoomViewModel extends AndroidViewModel {
    private boolean status = false;
    private FirebaseUser user;
    private MutableLiveData<String> enemyUserName;
    private MutableLiveData<Uri> enemyAvatarUri;
    private MutableLiveData<Boolean> enemyStatus;
    private String idRoom;
    private UserEnum userEnum;
    private UserEnum enemyEnum;


    public RoomViewModel(@NonNull Application application){
        super(application);
        idRoom = UUID.randomUUID().toString().substring(0, 6);
        userEnum = UserEnum.FIRST_USER;
        enemyEnum = UserEnum.SECOND_USER;
        setData(userEnum);
    }

    public UserEnum getUserEnum() {
        return userEnum;
    }

    public RoomViewModel(@NonNull Application application, String idRoom){
        super(application);
        this.idRoom = idRoom;
        userEnum = UserEnum.SECOND_USER;
        enemyEnum = UserEnum.FIRST_USER;
        setData(userEnum);
    }

    public String getIdRoom() {
        return idRoom;
    }

    public boolean getStatus(){
        return status;
    }

    public void changeStatus(){
        status = !status;
        Database.getCurrentUserStatus(idRoom, userEnum).setValue(status);
    }

    public Uri getPhotoUri(){
        if (user == null){
            return Uri.EMPTY;
        }

        return user.getPhotoUrl();
    }

    public String getUserName(){
        if(user == null){
            return "NoName";
        }
        return user.getDisplayName();
    }

    public MutableLiveData<String> getEnemyUserName(){
        if (enemyUserName == null){
            enemyUserName = new MutableLiveData<String>();
        }

        return enemyUserName;
    }

    public MutableLiveData<Uri> getEnemyAvatarUri() {
        if (enemyAvatarUri == null){
            enemyAvatarUri = new MutableLiveData<Uri>();
        }

        return enemyAvatarUri;
    }

    public MutableLiveData<Boolean> getEnemystatus() {
        if (enemyStatus == null){
            enemyStatus = new MutableLiveData<Boolean>();
            enemyStatus.setValue(false);
        }

        return enemyStatus;
    }

    private void setData(final UserEnum userEnum){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Database.getCurrentUserAvatar(idRoom, userEnum).setValue(getPhotoUri().toString());
        Database.getCurrentUsername(idRoom, userEnum).setValue(getUserName());

        Database.getCurrentUserRoomRef(idRoom, enemyEnum).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()){
                    if (Objects.equals(child.getKey(), "avatar")){
                        getEnemyAvatarUri().setValue(Uri.parse(Objects.requireNonNull(child.getValue().toString())));
                    }

                    if (Objects.equals(child.getKey(), "username")){
                        getEnemyUserName().setValue(Objects.requireNonNull(child.getValue().toString()));
                    }

                    if (Objects.equals(child.getKey(), "status")){
                        getEnemystatus().setValue(Boolean.parseBoolean(Objects.requireNonNull(child.getValue().toString())));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database.getCurrentUsername(idRoom, enemyEnum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    getEnemyUserName().setValue(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database.getCurrentUserAvatar(idRoom, enemyEnum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    getEnemyAvatarUri().setValue(Uri.parse(snapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Database.getCurrentUserStatus(idRoom, userEnum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    getEnemystatus().setValue(Boolean.parseBoolean(snapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
