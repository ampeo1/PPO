package com.example.battleship.game.map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;

import java.util.List;

public class GameMapAdapter extends RecyclerView.Adapter<GameMapAdapter.ViewHolder> {
    private List<Field> fields;
    private MutableLiveData<Boolean> turn;
    private int shot = 20;

    public GameMapAdapter(List<Field> fields, MutableLiveData<Boolean> turn){
        this.fields = fields;
        this.turn = turn;
    }

    @Override
    public GameMapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
        return new GameMapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GameMapAdapter.ViewHolder holder, final int position) {
        holder.image.setClickable(true);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn.getValue()){
                    Field field = fields.get(position);
                    Toast.makeText(v.getContext(), field.getStatus().name(), Toast.LENGTH_LONG).show();
                    if (field.getStatus() == StatusField.SHIP){
                        field.setStatus(StatusField.DESTROY_SHIP);
                        turn.setValue(false);
                        shot--;
                        notifyItemChanged(position);
                    }
                    if (field.getStatus() == StatusField.EMPTY){
                        field.setStatus(StatusField.SHOT);
                        turn.setValue(false);
                        notifyItemChanged(position);
                    }
                }
            }
        });
        switch(fields.get(position).getStatus()){
            case DESTROY_SHIP:
                holder.image.setImageResource(R.drawable.destroy_part_ship);
                holder.image.setImageAlpha(255);
                break;
            case SHOT:
                holder.image.setImageResource(R.drawable.shot);
                holder.image.setImageAlpha(255);
                break;
            default:
                holder.image.setImageResource(R.drawable.empty_field);
                holder.image.setImageAlpha(255);
                break;
        }
    }

    public int getShot(){
        return shot;
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView image;

        ViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.image_field);
        }
    }
}
