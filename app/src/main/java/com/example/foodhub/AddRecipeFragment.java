package com.example.foodhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.foodhub.steps.AddRecipeAdapter;
import com.example.foodhub.steps.Step;

import java.util.ArrayList;

public class AddRecipeFragment extends Fragment {

    private int step = 1;
    private ArrayList<EditText> descriptions;
    private ArrayList<EditText> times;

    private EditText name, description;
    private static final int RESULT_OK = 3;
    private Button gallery;
    private ImageView picture;

    private AddRecipeAdapter addRecipeAdapter;

    ArrayList<Step> steps = new ArrayList<Step>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }



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
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.newStepLayout);

        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.addrecipeview);
        addRecipeAdapter = new AddRecipeAdapter(getContext(), steps);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(addRecipeAdapter);

        Button addStep = (Button) view.findViewById(R.id.addRecipeNewStep);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                steps.add(new Step("a", "1"));

                addRecipeAdapter.notifyItemInserted(steps.size() - 1);

//                addRecipeAdapter = new AddRecipeAdapter(getContext(), steps);
//                recyclerView.setAdapter(addRecipeAdapter);


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

        picture = (ImageView) view.findViewById(R.id.addRecipeImage);
        gallery = (Button) view.findViewById(R.id.addRecipeImageButton);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_OK);
            }
        });
        return view;
    }
    private void setInitialData() {
        steps.add(new Step("a", "1"));
        steps.add(new Step("b", "2"));
    }
}