package com.example.battleship.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.MainActivity;
import com.example.battleship.R;
import com.example.battleship.game.map.GameMapAdapter;

public class GameActivity extends AppCompatActivity {
    private GameViewModel mViewModel;
    private GameMapAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mViewModel = new ViewModelProvider(this, new GameViewModelFactory(
                this.getApplication(), getIntent().getStringExtra("id_room"), getIntent().getStringExtra("user")))
                .get(GameViewModel.class);

        final RecyclerView recyclerView = getSupportFragmentManager().findFragmentById(R.id.my_ship_map).getActivity().findViewById(R.id.recyclerView);

        mViewModel.getChangeMapLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    adapter = new GameMapAdapter(mViewModel.getEnemyMap(), mViewModel.getTurn());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        mViewModel.getTurn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    mViewModel.changeTurn();
                    if(adapter.getShot() == 0){
                        mViewModel.finishGame();
                    }
                }
            }
        });

        mViewModel.getStatusGame().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}