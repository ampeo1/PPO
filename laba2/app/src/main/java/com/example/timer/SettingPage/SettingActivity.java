package com.example.timer.SettingPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.timer.Database;
import com.example.timer.R;

public class SettingActivity extends AppCompatActivity {
    SettingViewModel mViewModel;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;
    Spinner spinner;
    int sizeCoef;
    boolean del = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = getSharedPreferences(SettingHelper.NAME_SETTING, MODE_PRIVATE);
        prefEditor = settings.edit();
        int idTheme = settings.getInt(SettingHelper.Theme, R.style.AppTheme);
        setTheme(idTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);


        mViewModel.setKeyTheme(idTheme == R.style.DarkTheme);
        SwitchCompat viewSwitch = (SwitchCompat) findViewById(R.id.switchTheme);
        viewSwitch.setChecked(mViewModel.getKeyTheme());
        viewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mViewModel.getKeyTheme() != isChecked){
                    mViewModel.setKeyTheme(isChecked);
                    prefEditor.putInt("Theme", isChecked ? R.style.DarkTheme : R.style.AppTheme);
                }
            }
        });

        sizeCoef = settings.getInt("sizeCoef", 2);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(sizeCoef);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sizeCoef = progress;
                prefEditor.putInt("sizeCoef", sizeCoef);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        viewSwitch = (SwitchCompat) findViewById(R.id.settingDelete);
        viewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                del = isChecked;
            }
        });

        spinner = (Spinner)findViewById(R.id.languages);


        Button btnSave = (Button)findViewById(R.id.buttonSaveSetting);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(del){
                    Database db = new Database(getApplicationContext());
                    db.deleteTable();
                }
                if(spinner.getSelectedItem().toString().equals("Русский")){
                    prefEditor.putString("Language", "ru");
                }
                else{
                    prefEditor.putString("Language", "en");
                }
                prefEditor.apply();
                Intent intent = new Intent();
                intent.putExtra("recreate", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}