package com.example.foodhub.Add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodhub.Add.AddRecipeAdapter;
import com.example.foodhub.Add.Step;
import com.example.foodhub.R;
import com.example.foodhub.Recipe;

import java.io.Serializable;
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

    ArrayList<String> step_desc = new ArrayList<String>();
    ArrayList<String> step_duration = new ArrayList<String>();
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
        name       .setText(bundle.getString("recipe_name"));
        description.setText(bundle.getString("recipe_desc"));
        step_duration = bundle.getStringArrayList("step_duration_list");
        step_desc = bundle.getStringArrayList("step_desc_list");
        if (step_duration != null) {
            for (int i = 0; i < step_duration.size(); i++) {
                steps.add(new Step(step_desc.get(i), step_duration.get(i)));
            }
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendRecipe();
                Toast.makeText(getActivity(), "S", Toast.LENGTH_LONG).show();
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

                for (int i = 0; i < steps.size(); i++) {
                    step_desc.add(steps.get(i).getDesc());
                    step_duration.add(steps.get(i).getDuration());
                }

                addStepBundle.putStringArrayList("step_desc_list", step_desc);
                addStepBundle.putStringArrayList("step_duration_list", step_duration);

                Fragment ans = new add_new_step();
                ans.setArguments(addStepBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addNewRecipeHostLayout, ans);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




//                steps.add(new Step("a", "1"));
//                addRecipeAdapter.notifyItemInserted(steps.size() - 1);



//                Recipe r = new Recipe();
//                r.setName("newrec");
//                FirebaseDatabase.getInstance().getReference("Rec").push().setValue(r);
//                FirebaseDatabase.getInstance().getReference("Rec")
//                        .setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getActivity(), "S", Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(getActivity(), ":(", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

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
    }
}