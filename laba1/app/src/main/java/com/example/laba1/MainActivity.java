package com.example.laba1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        TextView version = findViewById(R.id.versionName);
        version.setText(BuildConfig.VERSION_NAME);
    }

}