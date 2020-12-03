package com.example.timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timer.AddCategoryPage.AddCategoryActivity;
import com.example.timer.AddSequencePage.AddSequenceActivity;
import com.example.timer.Sequence.Sequence;
import com.example.timer.SequencePage.SequenceActivity;
import com.example.timer.SettingPage.SettingActivity;
import com.example.timer.SettingPage.SettingHelper;
import com.example.timer.SettingPage.SettingViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Sequence> adapter;
    List<Sequence> list;
    SharedPreferences settings;
    int themeId;

    public final static String SEQUENCE = "sequence";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        installSetting();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database db = new Database(getApplicationContext());
        ListView listView = (ListView)findViewById(R.id.sequences_list_view);
        list = db.getSequence();
        adapter = new ArrayAdapter<Sequence>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SequenceActivity.class);
                intent.putExtra(SEQUENCE, adapter.getItem(position));
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);

        Button btn = findViewById(R.id.add);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AddSequenceActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn = findViewById(R.id.addCategory);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn = findViewById(R.id.buttonSetting);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

   @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.edit:
                Intent intent = new Intent(getBaseContext(), AddSequenceActivity.class);
                intent.putExtra("sequence", adapter.getItem(info.position));
                startActivityForResult(intent, 1);
                break;
            case R.id.delete:
                deleteSequence(info.position);
        }
        return super.onContextItemSelected(item);
    }

    private void deleteSequence(int index){
        Sequence sequence = adapter.getItem(index);
        Database db = new Database(getApplicationContext());

        db.deleteSequence(sequence);
        adapter.remove(sequence);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data.getBooleanExtra("refresh", true)){
            Database db = new Database(getApplicationContext());
            adapter.clear();
            adapter.addAll(db.getSequence());
            this.adapter.notifyDataSetChanged();
        }
        if(resultCode == RESULT_OK && data.getBooleanExtra("recreate", false)){
            recreate();
        }
    }

    private void installSetting(){
        settings = getSharedPreferences(SettingHelper.NAME_SETTING, MODE_PRIVATE);
        setTheme(settings.getInt(SettingHelper.Theme, R.style.AppTheme));

        int sizeCoef = settings.getInt("sizeCoef", 2);
        Resources res = getResources();
        Configuration configuration = new Configuration(res.getConfiguration());
        configuration.fontScale = SettingViewModel.startValue + sizeCoef * SettingViewModel.step;

       /* if(settings.contains("Language")){
            String language = settings.getString("Language", "ru");
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            configuration.locale = locale;
        }
*/
        res.updateConfiguration(configuration, res.getDisplayMetrics());
    }
}