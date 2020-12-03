package com.example.timer.AddSequencePage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timer.MainActivity;
import com.example.timer.R;
import com.example.timer.Sequence.Sequence;
import com.example.timer.Sequence.Timer.Category.Category;
import com.example.timer.SettingPage.SettingHelper;

public class AddSequenceActivity extends AppCompatActivity implements View.OnClickListener, AddTimerFragment.NoticeDialogListener {
    private AddSequenceViewModel addSequenceViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(SettingHelper.NAME_SETTING, MODE_PRIVATE);
        setTheme(settings.getInt(SettingHelper.Theme, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        addSequenceViewModel = new ViewModelProvider(this).get(AddSequenceViewModel.class);
        setContentView(R.layout.activity_add_sequence);

        Intent intent = getIntent();
        Sequence sequence = intent.getParcelableExtra(MainActivity.SEQUENCE);
        if(sequence != null)
            addSequenceViewModel.setSequence(sequence);

        Button btnAdd = findViewById(R.id.createButton);
        btnAdd.setOnClickListener(this);

        Button btnAddTimer = findViewById(R.id.addTimerButton);
        btnAddTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimerDialog();
            }
        });

        EditText edit = (EditText)findViewById(R.id.editAddName);
        edit.setText(addSequenceViewModel.getEditName());
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable str) {
                addSequenceViewModel.setEditName(str.toString());
            }
        });
        edit = (EditText)findViewById(R.id.editAddColor);
        edit.setText(addSequenceViewModel.getEditColor());
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable str) {
                addSequenceViewModel.setEditColor(str.toString());
            }
        });

    }
    public void showTimerDialog(){
        DialogFragment dialog = new AddTimerFragment();
        dialog.show(getSupportFragmentManager(), "tag");
    }

    public void onDialogPositiveClick(Category category){
        addSequenceViewModel.addTimer(category);
        RecyclerViewFragment fragment = (RecyclerViewFragment)getSupportFragmentManager().
                findFragmentById(R.id.recycler_view_fragment);
        if(fragment != null)
            fragment.notifyAdapterDataSetChanged();
    }

    @Override
    public void onClick(View view){
        addSequenceViewModel.save();
        Intent intent = new Intent();
        intent.putExtra("refresh", true);
        setResult(RESULT_OK, intent);
        finish();
    }


}