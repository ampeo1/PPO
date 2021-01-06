package com.example.battleship.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;

import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {
    private List<Statistic> statistics;

    public StatisticAdapter(List<Statistic> statistics){
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public StatisticAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_item, parent, false);
        return new StatisticAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticAdapter.ViewHolder holder, int position) {
        holder.idRoom.setText(statistics.get(position).idRoom);
        holder.result.setText(statistics.get(position).result);
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView idRoom;
        final TextView result;

        ViewHolder(View view) {
            super(view);
            idRoom = (TextView) view.findViewById(R.id.id_room);
            result = (TextView) view.findViewById(R.id.result);
        }
    }
}
