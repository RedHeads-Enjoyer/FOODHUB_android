package com.example.foodhub.Add;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Step;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.stepPos.setText("Этап " + Integer.toString(holder.getAdapterPosition() + 1));
        String s = state.getSec().toString();
        String m = state.getMin().toString();
        String h = state.getHour().toString();
        if (s.length() == 1) s = "0" + s;
        if (m.length() == 1) m = "0" + m;
        if (h.length() == 1) h = "0" + h;
        if (state.getMin() != 0 || state.getSec() != 0 || state.getHour() != 0) {
            holder.durationView.setText(h + ":" + m + ":" + s);
        }
        else {
            holder.durationView.setText("Длительность этапа не указана");
        }
        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                builder.setTitle("Удаление")
                        .setMessage("Вы  уверены, что хотите удалить этап " + Integer.toString(holder.getAdapterPosition() + 1) + "?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                steps.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyDataSetChanged();
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
        if (steps == null) return 0;
        return steps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView buttonView;
        final TextView descView, durationView, stepPos;
        ViewHolder(View view){
            super(view);
            stepPos = view.findViewById(R.id.AddStepPosition);
            descView = view.findViewById(R.id.desc);
            durationView = view.findViewById(R.id.duration);
            buttonView = view.findViewById(R.id.button_del_1);
        }
    }
}
