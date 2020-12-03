package com.example.laba1;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> inputData;
    private MutableLiveData<String> outputData;
    private MutableLiveData<Unit> inputSpinner;
    private MutableLiveData<Unit> outputSpinner;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getInputData(){
        if(inputData == null){
            inputData = new MutableLiveData<String>();
            inputData.setValue("");
        }
        return inputData;
    }

    public void setInputData(String str){
        if(inputData == null){
            inputData = new MutableLiveData<String>();
        }
        inputData.setValue(str);
    }

    public LiveData<String> getOutputData(){
        if(outputData == null){
            outputData = new MutableLiveData<String>();
            outputData.setValue("");
        }
        return outputData;
    }

    public void setOutputData(String str){
        if(outputData == null){
            outputData = new MutableLiveData<String>();
        }
        outputData.setValue(str);
    }


    public MutableLiveData<Unit> getInputSpinner(){
        if(inputSpinner == null){
            inputSpinner = new MutableLiveData<Unit>();
            inputSpinner.setValue(Unit.METERS);
        }
        return inputSpinner;
    }

    public void setInputSpinner(Unit unit){
        if(inputSpinner == null){
            inputSpinner = new MutableLiveData<Unit>();
        }
        inputSpinner.setValue(unit);
    }


    public MutableLiveData<Unit> getOutputSpinner(){
        if(outputSpinner == null){
            outputSpinner = new MutableLiveData<Unit>();
            outputSpinner.setValue(Unit.METERS);
        }
        return outputSpinner;
    }

    public void setOutputSpinner(Unit unit){
        if(outputSpinner == null){
            outputSpinner = new MutableLiveData<Unit>();
        }
        outputSpinner.setValue(unit);
    }

    public String convert(String data){
        String str = Converter.convert(data, getInputSpinner().getValue(), getOutputSpinner().getValue());
        setOutputData(str);
        return outputData.getValue();
    }

    public String convert(Unit output){
        setOutputData(Converter.convert(getInputData().getValue(), getInputSpinner().getValue(), output));
        return outputData.getValue();
    }

    public void coppy(TypeCoppy type, ClipboardManager clipboardManager){
        ClipData clip;
        if(type == TypeCoppy.INPUT){
            clip = ClipData.newPlainText("text", getInputData().getValue());
        }
        else{
            clip = ClipData.newPlainText("text", getOutputData().getValue());
        }
        clipboardManager.setPrimaryClip(clip);
        Toast toast = Toast.makeText(getApplication(), clip.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
