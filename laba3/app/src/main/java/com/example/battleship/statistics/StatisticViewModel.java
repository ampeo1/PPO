package com.example.battleship.statistics;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.battleship.model.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticViewModel extends AndroidViewModel {
    private ArrayList<Statistic> statistics = new ArrayList<>();

    public StatisticViewModel(@NonNull Application application) {
        super(application);
        Database.getStatisticCurrentReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statistics.add(snapshot.getValue(Statistic.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Statistic> getStatistic(){
        return statistics;
    }
}
