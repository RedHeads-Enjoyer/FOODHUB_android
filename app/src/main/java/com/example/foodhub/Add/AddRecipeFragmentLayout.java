package com.example.foodhub.Add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodhub.R;
import com.example.foodhub.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddRecipeFragmentLayout extends Fragment {

    private int step = 1;
    private ArrayList<EditText> descriptions;
    private ArrayList<EditText> times;

    private EditText name, description;
    private static final int RESULT_OK = 3;
    private Button gallery, send;
    private ImageView picture;

    private AddRecipeAdapter addRecipeAdapter;
    private Uri imageUri;

    private String mainImageUri;

    ArrayList<String> step_desc = new ArrayList<String>();
    ArrayList<Integer> step_sec= new ArrayList<Integer>();
    ArrayList<Integer> step_min= new ArrayList<Integer>();
    ArrayList<Integer> step_hour= new ArrayList<Integer>();

    ArrayList<Step> steps = new ArrayList<Step>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;
            picture.setImageURI(selectedImage);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe_layout, container, false);

        send        = view.findViewById(R.id.sendRecipeBtn);
        name        = view.findViewById(R.id.addRecipeName);
        description = view.findViewById(R.id.addRecipeDesc);
        picture     = view.findViewById(R.id.addRecipeImage);
        gallery     = view.findViewById(R.id.addRecipeImageButton);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle.getStringArrayList("step_desc_list") != null) {
            name       .setText(bundle.getString("recipe_name"));
            description.setText(bundle.getString("recipe_desc"));

            step_min  = bundle.getIntegerArrayList("step_min_list");
            step_sec  = bundle.getIntegerArrayList("step_sec_list");
            step_hour = bundle.getIntegerArrayList("step_hour_list");

            imageUri = Uri.parse(bundle.getString("main_image_uri"));

            picture.setImageURI(imageUri);


            step_desc = bundle.getStringArrayList("step_desc_list");
            if (step_desc != null) {
                for (int i = 0; i < step_desc.size(); i++) {
                    steps.add(new Step(step_desc.get(i), step_sec.get(i), step_min.get(i), step_hour.get(i)));
                }
            }
        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRecipe();
            }
        });



        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.addrecipeview);
        addRecipeAdapter = new AddRecipeAdapter(getContext(), steps);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(addRecipeAdapter);

        Button addStep = (Button) view.findViewById(R.id.addRecipeNewStep);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle addStepBundle = new Bundle();


                addStepBundle.putString("recipe_name", name.getText().toString().trim());
                addStepBundle.putString("recipe_desc", description.getText().toString().trim());
                addStepBundle.putString("main_image_uri", imageUri.toString());


                step_desc.clear();
                step_min .clear();
                step_hour.clear();
                step_sec .clear();

                for (int i = 0; i < steps.size(); i++) {
                    step_desc.add(steps.get(i).getDesc());
                    step_sec.add(steps.get(i).getSec());
                    step_min.add(steps.get(i).getMin());
                    step_hour.add(steps.get(i).getHour());
                }

                addStepBundle.putStringArrayList("step_desc_list", step_desc);
                addStepBundle.putIntegerArrayList("step_sec_list", step_sec);
                addStepBundle.putIntegerArrayList("step_min_list", step_min);
                addStepBundle.putIntegerArrayList("step_hour_list", step_hour);

                Fragment ans = new add_new_step();
                ans.setArguments(addStepBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addNewRecipeHostLayout, ans);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_OK);
            }
        });
        return view;
    }

    private void sendRecipe() {
        Recipe r = new Recipe();
        r.setName(name.getText().toString().trim());
        r.setDescription(description.getText().toString().trim());
        r.setSteps(steps);
        r.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        r.setDislike(0);
        r.setLike(0);
        r.setViews(0);
        Log.d("zxc", imageUri.toString());
        r.setImage(Uri.parse(imageUri.toString()));

        FirebaseDatabase.getInstance().getReference("Recipe").push().setValue(r);

    }
}