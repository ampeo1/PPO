package com.example.battleship.room;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.battleship.R;
import com.squareup.picasso.Picasso;

public class CreateRoomActivity extends AppCompatActivity {
    RoomViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        TextView text = findViewById(R.id.id_room);
        text.setText(text.getText() + mViewModel.getIdRoom());

        ImageView image = findViewById(R.id.first_avatar);
        Picasso.with(getApplicationContext())
                .load(mViewModel.getPhotoUri())
                .error(R.drawable.user)
                .into(image);

        text = findViewById(R.id.first_username);
        text.setText(mViewModel.getUserName());

        Button btn = findViewById(R.id.btn_ready);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.changeStatus();
                ImageView image = findViewById(R.id.first_status);
                if (mViewModel.getStatus()){
                    Picasso.with(getApplicationContext()).load(R.drawable.tick).into(image);
                }
                else{
                    Picasso.with(getApplicationContext()).load(R.drawable.cross).into(image);
                }
            }
        });

        final ImageView imageView = findViewById(R.id.second_avatar);
        mViewModel.getEnemyAvatarUri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .into(imageView);
            }
        });

        final TextView textView = findViewById(R.id.second_username);
        mViewModel.getEnemyUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        final ImageView enemyStatus = findViewById(R.id.second_status);
        mViewModel.getEnemystatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.tick)
                            .into(enemyStatus);
                }
                else{
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.cross)
                            .into(enemyStatus);
                }
            }
        });
    }
}