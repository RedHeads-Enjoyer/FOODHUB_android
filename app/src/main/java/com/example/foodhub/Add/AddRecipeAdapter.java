package com.example.foodhub.Add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;

import java.util.List;

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
        holder.durationView.setText(state.getDuration());
        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                steps.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button buttonView;
        final TextView descView, durationView;
        ViewHolder(View view){
            super(view);
            descView = view.findViewById(R.id.desc);
            durationView = view.findViewById(R.id.duration);
            buttonView = view.findViewById(R.id.button_del_1);
        }
    }
}
