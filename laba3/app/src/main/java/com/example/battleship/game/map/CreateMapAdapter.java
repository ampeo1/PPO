package com.example.battleship.game.map;

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

public class CreateMapAdapter extends RecyclerView.Adapter<CreateMapAdapter.ViewHolder> {
    private List<Field> fields;
    private int countShip = 0;

    public CreateMapAdapter(List<Field> fields){
        this.fields = fields;
    }

    @Override
    public CreateMapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
        return new CreateMapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreateMapAdapter.ViewHolder holder, final int position) {
        holder.image.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                int lengthShip;
                switch(action) {
                    case DragEvent.ACTION_DRAG_STARTED:

                    case DragEvent.ACTION_DRAG_LOCATION:

                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        lengthShip = Integer.parseInt(event.getClipDescription().getLabel().toString());
                        if (checkPlace(lengthShip, position)) {
                            setStatus(lengthShip, position, StatusField.HOVER);
                        }
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        if (fields.get(position).getStatus() == StatusField.HOVER) {
                            lengthShip = Integer.parseInt(event.getClipDescription().getLabel().toString());
                            setStatus(lengthShip, position, StatusField.EMPTY);
                        }
                        return true;

                    case DragEvent.ACTION_DROP:
                        if (fields.get(position).getStatus() == StatusField.HOVER){
                            lengthShip = Integer.parseInt(event.getClipDescription().getLabel().toString());
                            setStatus(lengthShip, position, StatusField.SHIP);
                            ImageView image = (ImageView)event.getLocalState();
                            image.setVisibility(View.GONE);
                            countShip++;
                            return true; // accepts the drop - place the ship
                        }
                        else{
                            return false;
                        }

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
                break;
            case HOVER:
                holder.image.setImageResource(R.drawable.part_ship);
                holder.image.setImageAlpha(127);
                break;
            case SHIP:
                holder.image.setImageResource(R.drawable.part_ship);
                holder.image.setImageAlpha(255);
                break;
        }
    }

    private boolean checkPlace(int length, int position){
        if ((position % 10) + length - 1 > 9){
            return false;
        }

        for(int i = 0; i < length; i++ ){
            if (fields.get(position + i).getStatus() == StatusField.SHIP && (position % 10) + length - 1 < 10){
                return false;
            }

            if (position + i > 9 && fields.get(position + i - 10).getStatus() == StatusField.SHIP){
                return false;
            }

            if (position + i < 90 && fields.get(position + i + 10).getStatus() == StatusField.SHIP){
                return false;
            }
        }

        if (position % 10 != 0 &&  position > 10 && fields.get(position - 11).getStatus() == StatusField.SHIP){
            return false;
        }

        if ( position % 10 != 0 && position < 90 && fields.get(position + 9).getStatus() == StatusField.SHIP){
            return false;
        }

        if ((position + length - 1) % 10 != 9 && (position + length - 1) > 10 && fields.get(position + length - 10).getStatus() == StatusField.SHIP){
            return false;
        }

        if ((position + length - 1) % 10 != 9 && (position + length - 1) < 90 && fields.get(position + length + 10).getStatus() == StatusField.SHIP){
            return false;
        }

        return true;
    }

    private void setStatus(int length, int position, StatusField statusField){
        for(int i = 0; i < length; i++){
            fields.get(position + i).setStatus(statusField);
            notifyItemChanged(position + i);
        }
    }

    public boolean checkCountShip(){
        Log.d("ShipCount", String.valueOf(countShip));
        return countShip == 10;
    }

    public List<Field> getFields(){
        return fields;
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
