package com.example.battleship;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileViewModel extends AndroidViewModel {
    private FirebaseUser user;
    private MutableLiveData<String> usernameLiveData = null;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public MutableLiveData<String> getUserNameLiveData(){
        if (usernameLiveData == null){
            usernameLiveData = new MutableLiveData<>(user.getDisplayName());
        }
        return usernameLiveData;
    }

    public void setUserName(String username){
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
        user.updateProfile(profileUpdate);
        usernameLiveData.setValue(user.getDisplayName());
    }

    public Uri getImageUri(){
        return user.getPhotoUrl();
    }

    public void setImageUri(Uri uri){
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(profileChangeRequest);
    }
}
