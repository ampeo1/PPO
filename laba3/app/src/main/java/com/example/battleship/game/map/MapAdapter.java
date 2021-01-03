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
            private boolean status = false;
            @Override
            public boolean onDrag(View v, DragEvent event) {

                // Defines a variable to store the action type for the incoming event
                final int action = event.getAction();

                // Handles each of the expected events
                switch(action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        status = true;
                        Log.d("ACTION_DRAG_ENTERED",String.valueOf(status));
                        // Applies a green tint to the View. Return true; the return value is ignored.
                        holder.image.setImageResource(R.drawable.part_ship);
                        fields.get(position).setStatus(StatusField.SHIP);

                        // Invalidate the view to force a redraw in the new tint
                        holder.image.invalidate();
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        holder.image.clearColorFilter();
                        holder.image.setImageResource(R.drawable.empty_field);
                        fields.get(position).setStatus(StatusField.EMPTY);
                        status = false;
                        Log.d("ACTION_DRAG_EXITED",String.valueOf(status));
                        return true;

                    case DragEvent.ACTION_DROP:

                        // Gets the item containing the dragged data

                        // Turns off any color tints
                        holder.image.clearColorFilter();

                        // Invalidates the view to force a redraw
                        holder.image.invalidate();
                        Log.d("ACTION_DRAG_DROP",String.valueOf(status));
                        return status;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Turns off any color tinting
                        holder.image.clearColorFilter();

                        // Invalidates the view to force a redraw
                        holder.image.invalidate();
                        Log.d("ACTION_DRAG_ENDED",String.valueOf(event.getResult()));
                        /*if (event.getResult()) {
                            Log.d("ACTION_DRAG_ENDED", "зашёл");
                            fields.get(position).setStatus(StatusField.SHIP);
                             notifyDataSetChanged();
                        }*/
                        notifyDataSetChanged();

                        // Does a getResult(), and displays what happened.

                        // returns true; the value is ignored.
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
                Log.d("Constructor", "EMPTY");
                break;
            case SHIP:
                holder.image.setImageResource(R.drawable.part_ship);
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
