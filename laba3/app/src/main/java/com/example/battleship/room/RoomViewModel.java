package com.example.battleship.room;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;

public class RoomViewModel extends AndroidViewModel {
    private DatabaseReference userRef;
    private DatabaseReference enemyRef;
    private boolean status = false;
    private FirebaseUser user;
    private MutableLiveData<String> enemyUserName;
    private MutableLiveData<Uri> enemyAvatarUri;
    private MutableLiveData<Boolean> enemyStatus;
    private String idRoom;


    public RoomViewModel(@NonNull Application application){
        super(application);
        idRoom = UUID.randomUUID().toString().substring(0, 6);
        setData(UserEnum.FIRST_USER);
    }

    public RoomViewModel(@NonNull Application application, String idRoom){
        super(application);
        this.idRoom = idRoom;
        setData(UserEnum.SECOND_USER);
    }

    public String getIdRoom() {
        return idRoom;
    }

    public boolean getStatus(){
        return status;
    }

    public void changeStatus(){
        status = !status;
        userRef.child("status").setValue(status);
    }

    public Uri getPhotoUri(){
        return user.getPhotoUrl();
    }

    public String getUserName(){
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
        }

        return enemyStatus;
    }

    private void setData(final UserEnum userEnum){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference(idRoom).child(userEnum.name());
        userRef.child("avatar").setValue(user.getPhotoUrl().toString());
        userRef.child("username").setValue(user.getDisplayName());

        if (userEnum == UserEnum.FIRST_USER){
            enemyRef = database.getReference(idRoom).child(UserEnum.SECOND_USER.name());
        }
        else{
            enemyRef = database.getReference(idRoom).child(UserEnum.FIRST_USER.name());
        }

        enemyRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

        enemyRef.child("username").addValueEventListener(new ValueEventListener() {
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

        enemyRef.child("avatar").addValueEventListener(new ValueEventListener() {
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

        enemyRef.child("status").addValueEventListener(new ValueEventListener() {
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
