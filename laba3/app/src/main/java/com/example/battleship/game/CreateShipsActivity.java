package com.example.battleship.game;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.battleship.R;

public class CreateShipsActivity extends AppCompatActivity {
    private static final String TAG_SHIP_ONE = "ship_one";
    private float x_cord = 0;
    private float y_cord = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ships);

        final ImageView ship = findViewById(R.id.ship_one);
        ship.setTag(TAG_SHIP_ONE);

        ship.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData= ClipData.newPlainText("","");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                return v.startDrag(clipData, dsb, v,0);
            }
        });

    }
}