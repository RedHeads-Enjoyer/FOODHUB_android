package com.example.foodhub.Add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.foodhub.R;

public class AddRecipeEditStep extends Fragment {
    NumberPicker secPicker, minPicker, hourPicker;
    Button confirm, cansel;
    EditText stepDesc;
    public AddRecipeEditStep() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe_edit_step, container, false);
        confirm = view.findViewById(R.id.addStepEditConfirm);
        cansel = view.findViewById(R.id.addStepEditCansel);
        secPicker   = view.findViewById(R.id.addStepEditSecPicker);
        minPicker   = view.findViewById(R.id.addStepEditMinPicker);
        hourPicker  = view.findViewById(R.id.addStepEditHourPicker);
        stepDesc = view.findViewById(R.id.addStepEditText);

        secPicker.setMaxValue(59);
        secPicker.setMinValue(0);

        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();

        stepDesc.setText(bundle.getString("step_desc"));
        secPicker.setValue(bundle.getInt("step_sec"));
        minPicker.setValue(bundle.getInt("step_min"));
        hourPicker.setValue(bundle.getInt("step_hour"));

        Bundle finalBundle = bundle;
        cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle sendBundle = new Bundle();
                sendBundle.putString("recipe_name", "asd");
                Fragment addrecipe = new AddRecipeFragmentLayout();
                addrecipe.setArguments(sendBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addNewRecipeHostLayout, addrecipe);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}