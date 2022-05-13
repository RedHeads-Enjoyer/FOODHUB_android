package com.example.foodhub.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;

import java.util.List;

public class SearchRecipeAdapter  extends RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    public SearchRecipeAdapter(LayoutInflater inflater, List<Recipe> recipes) {
        this.inflater = inflater;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_recipe_search_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view){
            super(view);
        }
    }
}