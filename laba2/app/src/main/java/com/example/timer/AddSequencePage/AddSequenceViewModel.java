package com.example.timer.AddSequencePage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.timer.Database;
import com.example.timer.Sequence.Sequence;
import com.example.timer.Sequence.Timer.Category.Category;
import com.example.timer.Sequence.Timer.Timer;

import java.util.ArrayList;

public class AddSequenceViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Timer>> timers;
    private MutableLiveData<String> editName;
    private MutableLiveData<String> editColor;
    private Sequence sequence;

    public AddSequenceViewModel(@NonNull Application application){
        super(application);
    }

    public ArrayList<Timer> getTimers(int id){
        if(timers == null){
            Database db = new Database(getApplication());
            timers = new MutableLiveData<ArrayList<Timer>>();
            timers.setValue(db.getTimers(id));
        }
        return timers.getValue();
    }

    public void addTimer(Category category){
        Timer timer = new Timer(category, 0);
        if(timers.getValue() != null){
            timers.getValue().add(timer);
        }
        else{
            timers.setValue(new ArrayList<Timer>());
            timers.getValue().add(timer);
        }
    }

    public ArrayList<Timer> getTimers(){
        if(timers != null){
            return timers.getValue();
        }
        return null;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public String getEditName(){
        if(editName == null){
            editName = new MutableLiveData<String>();
            if(sequence != null){
                editName.setValue(sequence.getName());
            }
            else{
                editName.setValue("");
            }
        }
        return editName.getValue();
    }

    public void setEditName(String editName) {
        this.editName.setValue(editName);
    }

    public String getEditColor() {
        if(editColor == null){
            editColor = new MutableLiveData<String>();
            if(sequence != null){
                editColor.setValue(sequence.getColor());
            }
            else{
                editColor.setValue("");
            }
        }
        return editColor.getValue();
    }

    public void setEditColor(String editColor) {
        this.editColor.setValue(editColor);
    }

    public void save(){
        Database db = new Database(getApplication());
        long idSequence;
        if(sequence == null){
            idSequence = db.addSequence(new Sequence(editName.getValue(), editColor.getValue()));
        }
        else{
            idSequence = sequence.getId();
            sequence.setColor(editColor.getValue());
            sequence.setName(editName.getValue());
            db.updateSequence(sequence);
        }
        for(Timer item: timers.getValue()){ // подумать над новыйм объектом
            if(item.getId() != -1)
                db.updateTimer(item);
            else
                db.addTimer(item, idSequence);
        }
    }

    public int getIdSequence(){
        if(sequence != null)
            return sequence.getId();
        else
            return -1;
    }
}
