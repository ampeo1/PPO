package com.example.timer.SequencePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.R;
import com.example.timer.Sequence.Timer.Timer;

import java.util.List;

public class SequenceAdapter extends RecyclerView.Adapter<SequenceAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Timer> timers;
    private Context context;

    public SequenceAdapter(Context context, List<Timer> timers) {
        this.timers = timers;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public SequenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_sequence_item, parent, false);
        return new SequenceAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SequenceAdapter.ViewHolder holder, int position) {
        Timer timer = timers.get(position);
        holder.nameTimer.setText(timer.getCategory().getName());
        holder.time.setText(String.valueOf(timer.getDuration()));
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameTimer;
        final TextView time;
        final Button btnNext;
        final Button btnPrev;
        ViewHolder(View view){
            super(view);
            nameTimer = (TextView) view.findViewById(R.id.textViewNameTimerPage);
            time = (TextView) view.findViewById(R.id.TextViewTime);
            btnNext = (Button) view.findViewById(R.id.buttonNext);

            btnPrev = (Button) view.findViewById(R.id.buttonPrev);

        }
    }
}
