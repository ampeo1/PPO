package com.example.timer.RecyclerViewTimer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.Database;
import com.example.timer.R;
import com.example.timer.Sequence.Timer.Timer;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Timer> timers;
    private Context context;

    public TimerAdapter(Context context, List<Timer> timers) {
        this.timers = timers;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public TimerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_timer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimerAdapter.ViewHolder holder, int position) {
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
        final EditText time;
        final Button btnIncrement;
        final Button btnDecrement;
        final ImageButton btnDelete;
        ViewHolder(View view){
            super(view);
            nameTimer = (TextView) view.findViewById(R.id.textViewNameTimer);
            time = (EditText) view.findViewById(R.id.editTextTime);
            time.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = String.valueOf(s);
                    if(!str.equals("")){
                        Timer timer = timers.get(getAdapterPosition());
                        timer.setDuration(Integer.parseInt(str));
                    }
                }
            });
            btnDecrement = (Button) view.findViewById(R.id.buttonDecrement);
            btnDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = String.valueOf(time.getText());
                    if(!str.equals("")){
                        int duration = Integer.parseInt(str);
                        if(duration > 0){
                            duration--;
                            time.setText(String.valueOf(duration));
                            notifyDataSetChanged();
                        }
                    }
                }
            });
            btnIncrement = (Button) view.findViewById(R.id.buttonIncrement);
            btnIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = String.valueOf(time.getText());
                    int duration = 1;
                    if(!str.equals("")) {
                        duration = Integer.parseInt(str);
                        duration++;
                    }
                    time.setText(String.valueOf(duration));
                    notifyDataSetChanged();
                }
            });
            btnDelete = (ImageButton) view.findViewById(R.id.buttonDeleteTimer);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timer timer = timers.remove(getAdapterPosition());
                    Database db = new Database(context);
                    db.deleteTimer(timer.getId());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
