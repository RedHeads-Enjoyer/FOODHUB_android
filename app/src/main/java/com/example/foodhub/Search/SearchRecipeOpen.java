package com.example.foodhub.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodhub.R;

public class SearchRecipeOpen extends Fragment {
    private TextView RecipeName, ViewsCounter, LikeCounter, DislikeCounter, RecipeDesc;
    public SearchRecipeOpen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_recipe_open, container, false);

        RecipeDesc = view.findViewById(R.id.OpenRecipeDesc);
        RecipeName = view.findViewById(R.id.OpenRecipeName);
        ViewsCounter = view.findViewById(R.id.OpenRecipeViewCounter);
        LikeCounter = view.findViewById(R.id.OpenRecipeLikeCounter);
        DislikeCounter = view.findViewById(R.id.OpenRecipeDislikeCounter);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        RecipeName.setText(bundle.getString("recipe_name"));

        return view;
    }
}