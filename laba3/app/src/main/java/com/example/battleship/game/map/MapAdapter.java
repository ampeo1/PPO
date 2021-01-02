package com.example.battleship.game.map;

import android.content.Context;
import android.graphics.Color;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        switch(fields.get(position).getStatus()){
            case EMPTY:
                holder.image.setImageResource(R.drawable.empty_field);
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
            image.setOnDragListener(new View.OnDragListener() {
                private boolean status = false;
                @Override
                public boolean onDrag(View v, DragEvent event) {

                    // Defines a variable to store the action type for the incoming event
                    final int action = event.getAction();

                    // Handles each of the expected events
                    switch(action) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            // Applies a green tint to the View. Return true; the return value is ignored.
                            image.setColorFilter(Color.GREEN);

                            // Invalidate the view to force a redraw in the new tint
                            image.invalidate();
                            status = true;
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:

                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            image.clearColorFilter();
                            image.setColorFilter(Color.RED);
                            status = false;
                            break;

                        case DragEvent.ACTION_DROP:

                            // Gets the item containing the dragged data

                            // Turns off any color tints
                            image.clearColorFilter();

                            // Invalidates the view to force a redraw
                            image.invalidate();
                            return status;

                        case DragEvent.ACTION_DRAG_ENDED:
                            if (event.getResult()) {
                                image.setImageResource(R.drawable.part_ship);
                            }
                            // Turns off any color tinting
                            image.clearColorFilter();

                            // Invalidates the view to force a redraw
                            image.invalidate();

                            // Does a getResult(), and displays what happened.

                            // returns true; the value is ignored.
                            break;

                        // An unknown action type was received.
                        default:
                            Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                            break;
                    }

                    return true;
                }
            });
        }
    }
}
