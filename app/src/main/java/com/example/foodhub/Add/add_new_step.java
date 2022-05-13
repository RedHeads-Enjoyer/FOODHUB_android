package com.example.foodhub.Add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.foodhub.R;

import java.util.ArrayList;

public class add_new_step extends Fragment {
    EditText addDesc, addDuration;
    Button confirmAdd;
    NumberPicker secPicker, minPicker, hourPicker;

    private String imageUri;


    public add_new_step() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        Bundle loadBundle = new Bundle();
        loadBundle = this.getArguments();

        View view = inflater.inflate(R.layout.fragment_add_new_step, container, false);

        addDesc     = view.findViewById(R.id.editAddDesc);
        confirmAdd  = view.findViewById(R.id.confirmAddingStep);
        secPicker   = view.findViewById(R.id.addStepSecPicker);
        minPicker   = view.findViewById(R.id.addStepMinPicker);
        hourPicker  = view.findViewById(R.id.addStepHourPicker);

        secPicker.setMaxValue(59);
        secPicker.setMinValue(0);

        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);

        Bundle finalLoadBundle = loadBundle;

        imageUri = loadBundle.getString("main_image_uri");
        ArrayList<String> step_desc = loadBundle.getStringArrayList("step_desc_list");
        ArrayList<Integer> step_sec = loadBundle.getIntegerArrayList("step_sec_list");
        ArrayList<Integer> step_min = loadBundle.getIntegerArrayList("step_min_list");
        ArrayList<Integer> step_hour = loadBundle.getIntegerArrayList("step_hour_list");

        assert loadBundle != null;
        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step_desc.add(addDesc.getText().toString().trim());
                step_sec.add(secPicker.getValue());
                step_min.add(minPicker.getValue());
                step_hour.add(hourPicker.getValue());




                bundle.putStringArrayList("step_desc_list", step_desc);
                bundle.putIntegerArrayList("step_min_list", step_min);
                bundle.putIntegerArrayList("step_sec_list", step_sec);
                bundle.putIntegerArrayList("step_hour_list", step_hour);

                bundle.putString("recipe_name", finalLoadBundle.getString("recipe_name"));
                bundle.putString("recipe_desc", finalLoadBundle.getString("recipe_desc"));

                bundle.putString("main_image_uri", imageUri);

                Fragment addrecipe = new AddRecipeFragmentLayout();
                addrecipe.setArguments(bundle);
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