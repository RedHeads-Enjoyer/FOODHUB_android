package com.example.foodhub.Search;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchRecipeSpace extends Fragment {

    EditText searchBar;
    Button searchBtn;
    RecyclerView recyclerView;
    SearchRecipeAdapter searchRecipeAdapter;
    DatabaseReference databaseReference;
    private Recipe recipe = new Recipe();

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();


    public SearchRecipeSpace() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        View view = inflater.inflate(R.layout.fragment_search_recipe_space, container, false);
        recipes.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipes.clear();
                searchRecipeAdapter.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                searchRecipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        searchBar = view.findViewById(R.id.searchBarText);
        searchBtn = view.findViewById(R.id.searchRecipeBtn);
        recyclerView = view.findViewById(R.id.searchRecipeRecyclerView);
        searchRecipeAdapter = new SearchRecipeAdapter(getContext(), recipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchRecipeAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searchBar.getText().toString().trim() == "Аким Пишенин") {
                    MediaPlayer mediaPlayer = MediaPlayer.create(inflater.getContext(), R.raw.sound2);
                    mediaPlayer.start();
                }
                recipes.clear();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        recipes.clear();
                        searchRecipeAdapter.notifyDataSetChanged();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Recipe recipe = dataSnapshot.getValue(Recipe.class);
                            if (recipe.getName().startsWith(searchBar.getText().toString().trim())) recipes.add(recipe);
                        }
                        searchRecipeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}