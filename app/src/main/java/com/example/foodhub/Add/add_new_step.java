package com.example.foodhub.Add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodhub.R;

public class add_new_step extends Fragment {
    EditText addDesc, addDuration;
    Button confirmAdd;


    public add_new_step() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_step, container, false);
        addDesc = view.findViewById(R.id.editAddDesc);
        addDuration = view.findViewById(R.id.editAddDuration);
        confirmAdd = view.findViewById(R.id.confirmAddingStep);

        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "S", Toast.LENGTH_LONG).show();
                Log.d("zxczxc","123");
            }
        });

        return inflater.inflate(R.layout.fragment_add_new_step, container, false);
    }
}