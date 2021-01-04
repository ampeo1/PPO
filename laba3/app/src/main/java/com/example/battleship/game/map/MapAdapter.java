package com.example.battleship.game.map;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;

import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    private final List<Field> fields;
    private Context context;

    public MapAdapter(List<Field> fields) {
        this.fields = fields;
        this.context = context;
    }

    @Override
    public MapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
        return new MapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MapAdapter.ViewHolder holder, final int position) {
        holder.image.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                // Defines a variable to store the action type for the incoming event
                final int action = event.getAction();

                // Handles each of the expected events
                switch(action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("ACTION_DRAG_ENTERED", "enter");
                        // Applies a green tint to the View. Return true; the return value is ignored.
                        if (fields.get(position).getStatus() == StatusField.EMPTY) {
                            fields.get(position).setStatus(StatusField.HOVER);
                            notifyItemChanged(position);
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (fields.get(position).getStatus() == StatusField.HOVER) {
                            fields.get(position).setStatus(StatusField.EMPTY);
                            notifyItemChanged(position);
                        }
                        Log.d("ACTION_DRAG_EXITED", "");
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.d("ACTION_DRAG_DROP", "");
                        if (fields.get(position).getStatus() == StatusField.SHIP) {
                            return false; // rejects the drop (field is not empty or allowed)
                        } else {
                            fields.get(position).setStatus(StatusField.SHIP);
                            notifyItemChanged(position);
                            return true; // accepts the drop - place the ship
                        }

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("ACTION_DRAG_ENDED",String.valueOf(event.getResult()));
                        /*if (event.getResult()) {
                            Log.d("ACTION_DRAG_ENDED", "зашёл");
                            fields.get(position).setStatus(StatusField.SHIP);
                             notifyDataSetChanged();
                        }*/
                        return true;

                    // An unknown action type was received.
                    default:
                        Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                        break;
                }

                return false;
            }
        });
        switch(fields.get(position).getStatus()){
            case EMPTY:
                holder.image.setImageResource(R.drawable.empty_field);
                holder.image.setImageAlpha(255);
                Log.d("Constructor", "EMPTY");
                break;
            case HOVER:
                holder.image.setImageResource(R.drawable.part_ship);
                // @todo: set proper icon here
                holder.image.setImageAlpha(127);
                Log.d("Constructor", "HOVER");
                break;
            case SHIP:
                holder.image.setImageResource(R.drawable.part_ship);
                holder.image.setImageAlpha(255);
                Log.d("Constructor", "SHIP");
                break;
        }
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
