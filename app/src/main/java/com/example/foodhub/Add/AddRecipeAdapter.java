package com.example.foodhub.Add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Step;

import java.util.List;
import java.util.Locale;

public class AddRecipeAdapter  extends RecyclerView.Adapter<AddRecipeAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Step> steps;


    public AddRecipeAdapter(Context context, List<Step> steps) {
        this.inflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @NonNull
    @Override
    public AddRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddRecipeAdapter.ViewHolder holder, int position) {
        Step state = steps.get(position);
        holder.descView.setText(state.getDesc());
        String s = state.getSec().toString();
        String m = state.getMin().toString();
        String h = state.getHour().toString();
        if (s.length() == 1) s = "0" + s;
        if (m.length() == 1) m = "0" + m;
        if (h.length() == 1) h = "0" + h;
        holder.durationView.setText(h + ":" + m + ":" + s);
        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                steps.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new AddRecipeEditStep();
//                Bundle bundle = new Bundle();
//                bundle.putString("recipe_name", state.getRecipeName());
//                bundle.putString("recipe_desc", state.getRecipeDesc());
//                bundle.putString("recipe_img", state.getRecipeImg());
//                bundle.putString("step_desc", state.getDesc());
//                bundle.putInt("step_min", state.getMin());
//                bundle.putInt("step_sec", state.getSec());
//                bundle.putInt("step_hour", state.getHour());
//                Fragment ares = new AddRecipeEditStep();
//                ares.setArguments(bundle);
//                FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.addNewRecipeHostLayout, ares);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button buttonView, editBtn;
        final TextView descView, durationView;
        ViewHolder(View view){
            super(view);
            descView = view.findViewById(R.id.desc);
            durationView = view.findViewById(R.id.duration);
            buttonView = view.findViewById(R.id.button_del_1);
            editBtn = view.findViewById(R.id.button_edit_l);
        }
    }
}
