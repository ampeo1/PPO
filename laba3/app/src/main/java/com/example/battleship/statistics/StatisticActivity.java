package com.example.battleship.statistics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;

public class StatisticActivity extends AppCompatActivity {
    StatisticViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        mViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.statistic_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StatisticAdapter(mViewModel.getStatistic()));
    }
}