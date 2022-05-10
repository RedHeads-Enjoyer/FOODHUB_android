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
import android.widget.Toast;

import com.example.foodhub.R;

import java.util.ArrayList;

public class add_new_step extends Fragment {
    EditText addDesc, addDuration;
    Button confirmAdd;


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
        addDuration = view.findViewById(R.id.editAddDuration);
        confirmAdd  = view.findViewById(R.id.confirmAddingStep);



        Bundle finalLoadBundle = loadBundle;
        ArrayList<Step> steps = new ArrayList<>();
        steps = (ArrayList<Step>) bundle.getSerializable("list");
        ArrayList<Step> finalSteps = steps;
        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("desc", addDesc.getText().toString().trim());
                bundle.putString("duration", addDuration.getText().toString().trim());
                bundle.putString("recipe_name", finalLoadBundle.getString("recipe_name"));
                bundle.putString("recipe_desc", finalLoadBundle.getString("recipe_desc"));
                finalSteps.add(new Step(addDesc.getText().toString().trim(), addDuration.getText().toString().trim()));
                bundle.putSerializable("list", finalSteps);
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