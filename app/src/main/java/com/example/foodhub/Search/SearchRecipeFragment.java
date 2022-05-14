package com.example.foodhub.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;
import com.google.firebase.auth.ActionCodeResult;

import java.util.ArrayList;

public class SearchRecipeFragment extends Fragment {

    EditText searchBar;
    Button searchBtn;

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();


    public SearchRecipeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_recipe, container, false);

        searchBar = view.findViewById(R.id.searchBarText);
        searchBtn = view.findViewById(R.id.searchRecipeBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Поиск...", Toast.LENGTH_LONG).show();
            }
        });


        searchBar.setText("asd");
        return view;
    }
}