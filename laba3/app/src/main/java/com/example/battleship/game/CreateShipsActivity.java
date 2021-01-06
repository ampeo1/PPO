package com.example.battleship.game;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;
import com.example.battleship.game.map.FragmentMap;
import com.example.battleship.game.map.CreateMapAdapter;
import com.example.battleship.room.UserEnum;

public class CreateShipsActivity extends AppCompatActivity {
    private static final String TAG_SHIP_ONE = "ship_one";
    private static final String TAG_SHIP_TWO = "ship_two";
    private static final String TAG_SHIP_THREE = "ship_three";
    private static final String TAG_SHIP_FOUR = "ship_four";
    CreateShipsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ships);
        mViewModel = new ViewModelProvider(this).get(CreateShipsViewModel.class);

        RecyclerView recyclerView = getSupportFragmentManager().findFragmentById(R.id.create_ship_map).getActivity().findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new CreateMapAdapter(mViewModel.getEmptyMap()));


        Button btn = findViewById(R.id.btn_ready);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMap fragment = (FragmentMap)getSupportFragmentManager().findFragmentById(R.id.create_ship_map);
                RecyclerView recyclerView = fragment.getActivity().findViewById(R.id.recyclerView);
                CreateMapAdapter adapter = (CreateMapAdapter)recyclerView.getAdapter();
                UserEnum userEnum = UserEnum.valueOf(getIntent().getStringExtra("user"));
                String idRoom = getIntent().getStringExtra("id_room");

                if (adapter != null && mViewModel.pushMap(adapter, userEnum, idRoom)){
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    intent.putExtra("id_room", idRoom);
                    intent.putExtra("user", userEnum.name());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Не готов", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView ship = findViewById(R.id.ship_one_1);
        ship.setTag(TAG_SHIP_ONE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("1","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_one_2);
        ship.setTag(TAG_SHIP_ONE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("1","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_one_3);
        ship.setTag(TAG_SHIP_ONE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("1","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_one_4);
        ship.setTag(TAG_SHIP_ONE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("1","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_two_1);
        ship.setTag(TAG_SHIP_TWO);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("2","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_two_2);
        ship.setTag(TAG_SHIP_TWO);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("2","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_two_3);
        ship.setTag(TAG_SHIP_TWO);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("2","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_three_1);
        ship.setTag(TAG_SHIP_THREE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("3","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_three_2);
        ship.setTag(TAG_SHIP_THREE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("3","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

        ship = findViewById(R.id.ship_four_1);
        ship.setTag(TAG_SHIP_FOUR);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("4","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

    }
}