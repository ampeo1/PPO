package com.example.timer.AddCategoryPage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.Database;
import com.example.timer.R;
import com.example.timer.RecyclerViewCategory.CategoryAdapter;
import com.example.timer.Sequence.Timer.Category.Category;
import com.example.timer.SettingPage.SettingHelper;

public class AddCategoryActivity extends AppCompatActivity {
    private AddCategoryViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(SettingHelper.NAME_SETTING, MODE_PRIVATE);
        setTheme(settings.getInt(SettingHelper.Theme, R.style.AppTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mViewModel = new ViewModelProvider(this).get(AddCategoryViewModel.class);

        EditText edit = (EditText) findViewById(R.id.editAddName);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = String.valueOf(s);
                mViewModel.setEditName(str);
            }
        });

        Button btn = (Button) findViewById(R.id.createCategoryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getApplicationContext());
                db.addCategory(new Category(mViewModel.getEditName()));
                finish();
            }
        });

        Database db = new Database(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryAdapter adapter = new CategoryAdapter(this, db.getCategory());
        recyclerView.setAdapter(adapter);
    }
}