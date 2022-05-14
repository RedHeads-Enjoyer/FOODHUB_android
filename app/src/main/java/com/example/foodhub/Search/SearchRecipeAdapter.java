package com.example.foodhub.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;

import java.util.List;

public class SearchRecipeAdapter  extends RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    public SearchRecipeAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
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
        Recipe recipe = recipes.get(position);
        holder.authorName.setText("Author");
        holder.recipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeName, authorName;
        final RatingBar recipeRating;
        final ImageView recipeImg;
        ViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.searchRecipeName);
            authorName = view.findViewById(R.id.searchRecipeAuthor);
            recipeRating = view.findViewById(R.id.searchRecipeRating);
            recipeImg = view.findViewById(R.id.searchRecipeImg);
        }
    }
}