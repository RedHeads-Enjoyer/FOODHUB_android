package com.example.foodhub.Add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodhub.R;

public class AddRecipeEditStep extends Fragment {

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
        TextView textView;
        textView = view.findViewById(R.id.editStepDesc);
        textView.setText("asd");
        return view;
    }
}