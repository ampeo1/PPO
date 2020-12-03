package com.example.timer.AddSequencePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.R;
import com.example.timer.RecyclerViewTimer.TimerAdapter;

public class RecyclerViewFragment extends Fragment {

    private AddSequenceViewModel mViewModel;
    private View view;
    private RecyclerView recyclerView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =  new ViewModelProvider(requireActivity()).get(AddSequenceViewModel.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTimer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        TimerAdapter adapter = new TimerAdapter(requireActivity(),mViewModel.getTimers(mViewModel.getIdSequence()));
        recyclerView.setAdapter(adapter);
    }

    public void notifyAdapterDataSetChanged(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}