package com.example.foodhub.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.foodhub.Step;
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

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeAdapter  extends RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    private boolean da = true;
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

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
                if (recipe.getWhoWatched().contains(userID)) {
                    holder.viewPic.setImageResource(R.drawable.cool_view);
                }
                else {
                    holder.viewPic.setImageResource(R.drawable.view);
                }
                if (recipe.getWhoLiked().contains(userID)) {
                    holder.likePic.setImageResource(R.drawable.cool_like);
                }
                else {
                    holder.likePic.setImageResource(R.drawable.like);
                }
                if (recipe.getWhoDisliked().contains(userID)) {
                    holder.dislikePic.setImageResource(R.drawable.cool_dislike);
                }
                else {
                    holder.dislikePic.setImageResource(R.drawable.dislike);
                }
                User userProfile = snapshot.getValue(User.class);
                Picasso.get().load(recipe.getImage()).resize(350, 350).placeholder(R.drawable.gal).centerCrop().into(holder.recipeImg);
                holder.authorName.setText(userProfile.getUsername());
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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipe.getWhoWatched() == null) {
                    FirebaseDatabase.getInstance().getReference("Recipe").child(recipe.getRecipeID()).child("views").setValue(recipe.getViews() + 1);
                    FirebaseDatabase.getInstance().getReference("Recipe").child(recipe.getRecipeID()).child("whoWatched").child( Integer.toString(recipe.getWhoWatched().size() + 1)).setValue(userID);
                }
                else
                if (!recipe.getWhoWatched().contains(userID)) {
                    FirebaseDatabase.getInstance().getReference("Recipe").child(recipe.getRecipeID()).child("views").setValue(recipe.getViews() + 1);
                    FirebaseDatabase.getInstance().getReference("Recipe").child(recipe.getRecipeID()).child("whoWatched").child(Integer.toString(recipe.getWhoWatched().size() + 1)).setValue(userID);
                }
                ArrayList<Integer> sec = new ArrayList<>();
                ArrayList<Integer> min = new ArrayList<>();
                ArrayList<Integer> hour = new ArrayList<>();
                ArrayList<String> stepDesc = new ArrayList<>();
                ArrayList<Step> steps = new ArrayList<>();
                steps = recipe.getSteps();

                for (int i = 0; i < steps.size(); i++) {
                    sec.add(steps.get(i).getSec());
                    min.add(steps.get(i).getMin());
                    hour.add(steps.get(i).getHour());
                    stepDesc.add(steps.get(i).getDesc());
                }

                Bundle bundle = new Bundle();

                bundle.putIntegerArrayList("step_sec", sec);
                bundle.putIntegerArrayList("step_min", min);
                bundle.putIntegerArrayList("step_hour", hour);
                bundle.putStringArrayList("step_desc", stepDesc);

                bundle.putInt("recipe_like", recipe.getLike());
                bundle.putInt("recipe_dislike", recipe.getDislike());
                bundle.putString("recipe_ID", recipe.getRecipeID());
                bundle.putString("recipe_name", recipe.getName());
                bundle.putString("recipe_desc", recipe.getDescription());
                bundle.putInt("recipe_views", recipe.getViews());
                bundle.putInt("recipe_likes", recipe.getLike());
                bundle.putInt("recipe_dislike", recipe.getDislike());
                bundle.putString("recipe_img", recipe.getImage());
                bundle.putString("username", recipe.getUsername());
                bundle.putStringArrayList("who_liked", recipe.getWhoLiked());
                bundle.putStringArrayList("who_disliked", recipe.getWhoDisliked());
                Log.d("qwe", Integer.toString(recipe.getWhoDisliked().size()));

                Fragment sro = new SearchRecipeOpen();
                sro.setArguments(bundle);
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.SearchRecipeHostLayput, sro);
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
        final ImageView likePic, dislikePic, viewPic;
        final ConstraintLayout layout;
        ViewHolder(View view){
            super(view);
            likePic = view.findViewById(R.id.SearchRecipeLike);
            viewPic = view.findViewById(R.id.SearchRecipeViews);
            dislikePic = view.findViewById(R.id.SearchRecipeDislike);
            recipeName = view.findViewById(R.id.searchReciperName);
            authorName = view.findViewById(R.id.searchRecipeAuthor);
            recipeImg = view.findViewById(R.id.searchRecipeImg);
            likeDislikeBar = view.findViewById(R.id.SearchRecipeRetingBar);
            viewsCounter = view.findViewById(R.id.searchRecipeViewsCount);
            likeDislikeCounter = view.findViewById(R.id.SearchRecipeLikeDislikeCounter);
            layout = view.findViewById(R.id.profileAdapterLayoutClick);
        }
    }
}