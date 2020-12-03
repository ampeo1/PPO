package com.example.timer.AddCategoryPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddCategoryViewModel extends AndroidViewModel {
    private MutableLiveData<String> editName = new MutableLiveData<String>();
    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public String getEditName() {
        return editName.getValue();
    }

    public void setEditName(String editName) {
        this.editName.setValue(editName);
    }
}
