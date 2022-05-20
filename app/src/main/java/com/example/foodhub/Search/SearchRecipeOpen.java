package com.example.foodhub.Search;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.R;
import com.example.foodhub.Step;
import com.example.foodhub.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchRecipeOpen extends Fragment {

    private ArrayList<Integer> sec = new ArrayList<>();
    private ArrayList<Integer> min = new ArrayList<>();
    private ArrayList<Integer> hour = new ArrayList<>();
    private ArrayList<String> stepDesc = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();
    private ArrayList<String> whoLiked = new ArrayList<>();
    private ArrayList<String> whoDisliked = new ArrayList<>();
    private Bundle bundle;
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    int likeInd;
    int disLikeInd;

    private TextView RecipeName, ViewsCounter, LikeCounter, DislikeCounter, RecipeDesc, Username;
    private ImageView RecipeImg;
    private RecyclerView recyclerView;
    private Button LikeBtn, DislikeBtn, returnBtn;

    private String recipeID;
    private int likeC, dislikeC;
    private boolean l, dl;

    OpenRecipeStepsAdapter openRecipeStepsAdapter;

    public SearchRecipeOpen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        View view = inflater.inflate(R.layout.fragment_search_recipe_open, container, false);

        RecipeDesc = view.findViewById(R.id.OpenRecipeDesc);
        RecipeName = view.findViewById(R.id.OpenRecipeName);
        ViewsCounter = view.findViewById(R.id.OpenRecipeViewCounter);
        LikeCounter = view.findViewById(R.id.OpenRecipeLikeCounter);
        DislikeCounter = view.findViewById(R.id.OpenRecipeDislikeCounter);
        Username = view.findViewById(R.id.OpenRecipeUsername);
        RecipeImg = view.findViewById(R.id.OpenRecipeImage);
        LikeBtn = view.findViewById(R.id.OpenRecipeLikeBtn);
        DislikeBtn = view.findViewById(R.id.OpenRecipeDislikeBtn);
        returnBtn = view.findViewById(R.id.OprenRecipeReturnButton);

        // Вернуться к фрагменту поиска
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment srs = new SearchRecipeSpace();
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.SearchRecipeHostLayput, srs);
                fragmentTransaction.commit();
            }
        });

        recyclerView = view.findViewById(R.id.OpenRecipeRecyclerView);

        // Получение данных из бандла
        bundle = new Bundle();
        bundle = this.getArguments();
        RecipeName.setText(bundle.getString("recipe_name"));
        RecipeDesc.setText(bundle.getString("recipe_desc"));
        ViewsCounter.setText(Integer.toString(bundle.getInt("recipe_views")));
        LikeCounter.setText(Integer.toString(bundle.getInt("recipe_likes")));
        DislikeCounter.setText(Integer.toString(bundle.getInt("recipe_dislike")));
        Username.setText(bundle.getString("username"));

        sec         = bundle.getIntegerArrayList("step_sec");
        min         = bundle.getIntegerArrayList("step_min");
        hour        = bundle.getIntegerArrayList("step_hour");
        stepDesc    = bundle.getStringArrayList("step_desc");
        recipeID    = bundle.getString("recipe_ID");
        likeC       = bundle.getInt("recipe_like");
        dislikeC    = bundle.getInt("recipe_dislike");
        whoLiked    = bundle.getStringArrayList("who_liked");
        whoDisliked = bundle.getStringArrayList("who_disliked");

        // установка цвета кнопок оценки и установка их в правильное положение
        l = false;
        dl = false;
        if (whoDisliked.contains(userID)) {
            Log.d("zxc", "1");
            for (int i = 0; i < whoDisliked.size(); i++) {
                if (whoDisliked.get(i) == userID) {
                    disLikeInd = i;
                    break;
                }
            }
            LikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
            DislikeBtn.setBackgroundColor(getResources().getColor(R.color.uncliked_btn));
            dislikeC = dislikeC - 1;
            dl = true;
        }
        else disLikeInd = whoDisliked.size();
        if (whoLiked.contains(userID)){
            Log.d("zxc", "2");
            for (int i = 0; i < whoLiked.size(); i++) {
                if (whoLiked.get(i) == userID) {
                    likeInd = i;
                    break;
                }
            }
            LikeBtn.setBackgroundColor(getResources().getColor(R.color.uncliked_btn));
            DislikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
            likeC = likeC - 1;
            l = true;
        }
        else likeInd = whoLiked.size();

        for (int i=0; i < stepDesc.size(); i++) {
            steps.add(new Step(stepDesc.get(i), sec.get(i), min.get(i), hour.get(i)));
        }

        // Установка RecyclerView
        openRecipeStepsAdapter = new OpenRecipeStepsAdapter(getContext(), steps);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(openRecipeStepsAdapter);

        Picasso.get().load(bundle.getString("recipe_img")).resize(350, 350).placeholder(R.drawable.gal).centerCrop().into(RecipeImg);

        // Кнопка понравилось
        LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Recipe").child(recipeID);
                if (l) {
                    l = false;
                    LikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
                    databaseReference.child("like").setValue(likeC);
                    databaseReference.child("whoLiked").child(Integer.toString(likeInd)).removeValue();
                    return;
                }
                if (!l) {
                    if (dl) {
                        databaseReference.child("dislike").setValue(dislikeC);
                        databaseReference.child("whoDisliked").child(Integer.toString(disLikeInd)).removeValue();
                    }
                    dl = false;
                    l = true;
                    databaseReference.child("like").setValue(likeC + 1);
                    databaseReference.child("whoLiked").child(Integer.toString(likeInd)).setValue(userID);
                    LikeBtn.setBackgroundColor(getResources().getColor(R.color.uncliked_btn));
                    DislikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
                }
            }
        });

        // кнопка не понравилось
        DislikeBtn.setOnClickListener(new View.OnClickListener() {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Recipe").child(recipeID);
            @Override
            public void onClick(View view) {
                if (dl) {
                    dl = false;
                    DislikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
                    databaseReference.child("dislike").setValue(dislikeC);
                    databaseReference.child("whoDisliked").child(Integer.toString(disLikeInd)).removeValue();
                    return;
                }
                if (!dl) {
                    if (l) {
                        databaseReference.child("like").setValue(likeC);
                        databaseReference.child("whoLiked").child(Integer.toString(likeInd)).removeValue();
                    }
                    dl = true;
                    l = false;
                    LikeBtn.setBackgroundColor(getResources().getColor(R.color.cliked_btn));
                    DislikeBtn.setBackgroundColor(getResources().getColor(R.color.uncliked_btn));
                    databaseReference.child("whoDisliked").child(Integer.toString(disLikeInd)).setValue(userID);
                    databaseReference.child("dislike").setValue(dislikeC + 1);
                }
            }
        });
        return view;
    }
}