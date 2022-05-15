package com.example.foodhub.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;
import com.example.foodhub.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        FirebaseUser user;
        DatabaseReference reference;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(recipe.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                Picasso.get().load(recipe.getImage()).resize(350, 350).placeholder(R.drawable.gal).centerCrop().into(holder.recipeImg);
                holder.authorName.setText(userProfile.getUsername());
                holder.recipeName.setText(recipe.getName());
                holder.viewsCounter.setText(recipe.getViews().toString());
                holder.likeDislikeCounter.setText(recipe.getLike() + "/" + recipe.getDislike());
                if (recipe.getDislike() + recipe.getLike() == 0) {
                    holder.likeDislikeBar.setSecondaryProgress(0);
                }
                else {
                    holder.likeDislikeBar.setSecondaryProgress(recipe.getLike() / (recipe.getDislike() + recipe.getLike()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("recipe_name", recipe.getName());
                Fragment sro = new SearchRecipeOpen();
                sro.setArguments(bundle);
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.SearchRecipeHostLayput, sro);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeName, authorName, viewsCounter, likeDislikeCounter;
        final ImageView recipeImg;
        final ProgressBar likeDislikeBar;
        final ConstraintLayout layout;
        ViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.searchRecipeName);
            authorName = view.findViewById(R.id.searchRecipeAuthor);
            recipeImg = view.findViewById(R.id.searchRecipeImg);
            likeDislikeBar = view.findViewById(R.id.SearchRecipeRetingBar);
            viewsCounter = view.findViewById(R.id.SearchRecipeViewsCount);
            likeDislikeCounter = view.findViewById(R.id.SearchRecipeLikeDislikeCounter);
            layout = view.findViewById(R.id.SearchRecipeLayoutClick);
        }
    }
}