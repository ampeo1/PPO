package com.example.battleship;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityViewModel extends AndroidViewModel {
    private FirebaseUser user;
    private MutableLiveData<String> idRoom;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean isUser(){
        return user == null;
    }

    public void connectRoom(final String code){
        FirebaseDatabase.getInstance().getReference(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    getIdRoom().setValue(code);
                }
                else{
                    Toast.makeText(getApplication(), "Комната не найдена", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<String> getIdRoom(){
        if (idRoom == null){
            idRoom = new MutableLiveData<String>();
        }
        return idRoom;
    }
}
