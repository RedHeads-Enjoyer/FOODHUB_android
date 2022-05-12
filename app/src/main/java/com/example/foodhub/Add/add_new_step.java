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


        ArrayList<String> step_desc = loadBundle.getStringArrayList("step_desc_list");
        ArrayList<String> step_duration = loadBundle.getStringArrayList("step_duration_list");

        Log.d("zxc", "size " + step_desc.size());

        assert loadBundle != null;
        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                step_desc.add(addDesc.getText().toString().trim());

                Log.d("zxc", "size " + step_desc.size());
                step_duration.add(addDuration.getText().toString().trim());
                bundle.putStringArrayList("step_desc_list", step_desc);
                bundle.putStringArrayList("step_duration_list", step_duration);
                bundle.putString("recipe_name", finalLoadBundle.getString("recipe_name"));
                bundle.putString("recipe_desc", finalLoadBundle.getString("recipe_desc"));

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