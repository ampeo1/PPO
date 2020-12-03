package com.example.timer.SequencePage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.timer.Database;
import com.example.timer.Sequence.Timer.Timer;

import java.util.ArrayList;

public class SequenceViewModel extends AndroidViewModel {

    ArrayList<Timer> timers;
    public SequenceViewModel(@NonNull Application application) {
        super(application);
    }
    private MutableLiveData<String> time = new MutableLiveData<>();

    public ArrayList<Timer> getTimers(int id) {
        if (timers == null) {
            Database db = new Database(getApplication());
            timers = db.getTimers(id);
        }
        return timers;
    }

    public MutableLiveData<String> getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time.setValue(time);
    }

    public int[] getDurations(){
        int[] durations = new int[timers.size()];
        int i;
        for(i = 0; i < timers.size(); i++){
            durations[i] = timers.get(i).getDuration();
        }
        return durations;
    }
}
