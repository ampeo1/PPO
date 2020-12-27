package com.example.laba1;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldsDark extends Fields {
    ClipboardManager clipboard;
    MainViewModel mainViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fileds_fragment, container, false);
        baseFunctionality(inflater, container, savedInstanceState, layout);

        final Observer<Unit> inputSpinnerObserver = new Observer<Unit>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(Unit newInput) {
                Unit[] units = Arrays.stream(Unit.values())
                        .filter(value -> value.getCategory() == newInput.getCategory() && mainViewModel.getOutputSpinner().getValue() != value)
                        .toArray(Unit[]::new);
                if(newInput.getCategory() == mainViewModel.getOutputSpinner().getValue().getCategory()){
                    ArrayList<Unit> temp = new ArrayList<>(Arrays.asList(units));
                    temp.add(0,  mainViewModel.getOutputSpinner().getValue());
                    units = temp.toArray(new Unit[0]);
                }
                Spinner outputSpinner = (Spinner)layout.findViewById(R.id.outputSpinner);
                setSpinner(layout, outputSpinner, units, mainViewModel.getOutputSpinner());
            }
        };


        mainViewModel.getInputSpinner().observe(getViewLifecycleOwner(), inputSpinnerObserver);

        clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        layout.findViewById(R.id.coppyInput).setOnClickListener(item -> mainViewModel.coppy(TypeCoppy.INPUT, clipboard));
        layout.findViewById(R.id.coppyOutput).setOnClickListener(item -> mainViewModel.coppy(TypeCoppy.OUTPUT, clipboard));
        return layout;
    }
    
}