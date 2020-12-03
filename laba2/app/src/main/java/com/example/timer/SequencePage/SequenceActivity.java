package com.example.timer.SequencePage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.MainActivity;
import com.example.timer.R;
import com.example.timer.Sequence.Sequence;
import com.example.timer.Sequence.Timer.Timer;
import com.example.timer.Service.TimerService;
import com.example.timer.SettingPage.SettingHelper;

import java.util.List;

public class SequenceActivity extends AppCompatActivity {

    SequenceViewModel mViewModel;
    ArrayAdapter<Timer> adapter;
    List<Timer> list;
    private int position = 0;

    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    public final static String PARAM_RESULT = "result";
    public final static String PARAM_INPUT = "durations";
    public final static String TYPE_ACTION = "action";
    public final static String POSITION = "position";

    private BroadcastReceiver br;
    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(SettingHelper.NAME_SETTING, MODE_PRIVATE);
        setTheme(settings.getInt(SettingHelper.Theme, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);
        mViewModel = new ViewModelProvider(this).get(SequenceViewModel.class);


        Intent intent = getIntent();
        Sequence sequence = intent.getParcelableExtra(MainActivity.SEQUENCE);
        try{
            ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.SequenceLayout);
            layout.setBackgroundColor(Color.parseColor(sequence.getColor()));
        }
        catch(Exception ex){
            Log.d("ColorSequence", ex.getMessage());
        }
        list = mViewModel.getTimers(sequence.getId());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTimerPage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SequenceAdapter adapter = new SequenceAdapter(this, list);
        recyclerView.setAdapter(adapter);

        MutableLiveData<String> time = mViewModel.getTime();
        final TextView textViewTime = findViewById(R.id.TextViewTimer);
        time.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewTime.setText(s);
            }
        });

        intentService = new Intent(getApplicationContext(), TimerService.class);
        intentService.putExtra(PARAM_INPUT, mViewModel.getDurations());

        Button btn = (Button)findViewById(R.id.buttonStart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService.putExtra(TYPE_ACTION, (Parcelable) ActionType.START);
                startService(intentService);
            }
        });
        btn = (Button) findViewById(R.id.buttonStop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService.putExtra(TYPE_ACTION, (Parcelable) ActionType.STOP);
                startService(intentService);
            }
        });
        btn = (Button) findViewById(R.id.buttonNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService.putExtra(TYPE_ACTION, (Parcelable) ActionType.NEXT);
                startService(intentService);
            }
        });
        btn = (Button) findViewById(R.id.buttonPrev);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentService.putExtra(TYPE_ACTION, (Parcelable) ActionType.PREV);
                startService(intentService);
            }
        });

        br = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                long time = intent.getLongExtra(PARAM_RESULT, 0);
                int pos = intent.getIntExtra(POSITION, 0);
                mViewModel.setTime(String.valueOf(time));
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilt);
    }
}