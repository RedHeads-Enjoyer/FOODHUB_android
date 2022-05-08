package com.example.foodhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;

public class AddRecipeFragment extends Fragment {

    private int step = 1;
    private ArrayList<EditText> descriptions;
    private ArrayList<EditText> times;

    private EditText name, description;
    private static final int RESULT_OK = 3;
    private Button gallery;
    private ImageView picture;
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
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.newStepLayout);

        Button addStep = (Button) view.findViewById(R.id.addRecipeNewStep);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe r = new Recipe();
                r.setName("newrec");
                FirebaseDatabase.getInstance().getReference("Rec")
                        .setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "S", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), ":(", Toast.LENGTH_LONG).show();
                        }
                    }
                });
//                TextView countStep = new TextView(view.getContext());
//                EditText description = new EditText(view.getContext());
//                EditText time = new EditText(view.getContext());
//
//                descriptions.add(description);
//                times.add(time);
//
//                countStep.setText("Этап " + step);
//                description.setHint("описание");
//                time.setHint("время");
//                linearLayout.addView(countStep);
//                linearLayout.addView(description);
//                linearLayout.addView(time);
//                step += 1;
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
}