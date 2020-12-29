package com.example.battleship.Storage;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Storage {

    public static void SaveAvatar(Uri uri){
        if (uri == null){
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference riversRef = storageRef.child("avatars/" + uri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }

                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    final Uri downloadUri = task.getResult();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                            .Builder().setPhotoUri(downloadUri).build();
                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileChangeRequest);
                }
            }
        });
    }
}
