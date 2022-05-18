package com.example.foodhub.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.foodhub.Search.OpenRecipeStepsAdapter;
import com.example.foodhub.Search.SearchRecipeOpen;
import com.example.foodhub.Step;
import com.example.foodhub.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileRecipeAdapter extends RecyclerView.Adapter<ProfileRecipeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    public ProfileRecipeAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_profle_recipe_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecipeAdapter.ViewHolder holder, int position) {
        int pos = position;
        Recipe recipe = recipes.get(pos);
        FirebaseUser user;
        DatabaseReference reference;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(recipe.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                Picasso.get().load(recipe.getImage()).resize(350, 350).placeholder(R.drawable.gal).centerCrop().into(holder.recipeImg);
                recipe.setUsername(userProfile.getUsername());
                holder.recipeName.setText(recipe.getName());
                holder.viewsCounter.setText(recipe.getViews().toString());
                holder.likeDislikeCounter.setText(recipe.getLike() + "/" + recipe.getDislike());
                if (recipe.getDislike() + recipe.getLike() == 0) {
                    holder.likeDislikeBar.setSecondaryProgress(0);
                }
                else {
                    float l = (float) recipe.getLike();
                    float dl = (float) recipe.getDislike();
                    holder.likeDislikeBar.setSecondaryProgress((int) ((l / (l + dl)) * 100));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                builder.setTitle("Удаение")
                        .setMessage("Вы  уверены, что хотите удалить рецепт " + recipe.getName() + "?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StorageReference databaseReference = FirebaseStorage.getInstance().getReferenceFromUrl(recipe.getImage());
                                databaseReference.delete();
                                FirebaseDatabase.getInstance().getReference("Recipe").child(recipe.getRecipeID()).removeValue();
                                recipes.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(inflater.getContext(), "Рецепт удален", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeName, viewsCounter, likeDislikeCounter;
        final ImageView recipeImg;
        final ProgressBar likeDislikeBar;
        final Button deleteBtn;
        ViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.ProfileReciperName);
            viewsCounter = view.findViewById(R.id.ProfileRecipeViewsCount);
            likeDislikeCounter = view.findViewById(R.id.ProfileRecipeLikeDislikeCounter);
            recipeImg = view.findViewById(R.id.ProfileRecipeImg);
            likeDislikeBar = view.findViewById(R.id.ProfileRecipeRetingBar);
            deleteBtn = view.findViewById(R.id.ProfileRecipeDeleteBtn);
        }
    }

}
