package com.example.laba1;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;

public class Fields extends Fragment {

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
        Spinner inputSpinner = (Spinner) layout.findViewById(R.id.inputSpinner);
        setSpinner(layout, inputSpinner, Unit.values(), mainViewModel.getInputSpinner());


        final EditText edit = layout.findViewById(R.id.inputField);
        final TextView outputField = layout.findViewById(R.id.outputField);
        final Observer<String> inputObserver = new Observer<String>(){
            @Override
            public void onChanged(String newInput) {
                edit.setText(newInput);
                outputField.setText(mainViewModel.convert(newInput));
            }
        };

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

        final Observer<Unit> outputSpinnerObserver = new Observer<Unit>(){
            @Override
            public void onChanged(Unit newOutput){
                outputField.setText(mainViewModel.convert(newOutput));
            }
        };

        mainViewModel.getInputData().observe(getViewLifecycleOwner(), inputObserver);
        mainViewModel.getInputSpinner().observe(getViewLifecycleOwner(), inputSpinnerObserver);
        mainViewModel.getOutputSpinner().observe(getViewLifecycleOwner(), outputSpinnerObserver);

        clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        layout.findViewById(R.id.coppyInput).setOnClickListener(item -> mainViewModel.coppy(TypeCoppy.INPUT, clipboard));
        layout.findViewById(R.id.coppyOutput).setOnClickListener(item -> mainViewModel.coppy(TypeCoppy.OUTPUT, clipboard));
        return layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setSpinner(View layout, Spinner spinner, Unit[] units, MutableLiveData<Unit> liveSpinner){
        String[] unitsSpinner = Arrays.stream(units).map(value -> value.name()).toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item , unitsSpinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                Unit unit = Unit.valueOf(item);
                liveSpinner.setValue(unit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    
}