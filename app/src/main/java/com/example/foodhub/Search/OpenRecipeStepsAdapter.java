package com.example.foodhub.Search;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.R;
import com.example.foodhub.Step;

import java.util.List;
import java.util.Locale;

public class OpenRecipeStepsAdapter extends RecyclerView.Adapter<OpenRecipeStepsAdapter.ViewHolder> {

    final LayoutInflater inflater;
    final List<Step> steps;
    private Step step;

    private Handler handler = new Handler();


    MediaPlayer mediaPlayer;

    public OpenRecipeStepsAdapter(Context context, List<Step> steps) {
        this.inflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.open_recipe_step, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        step = steps.get(holder.getAdapterPosition());
        holder.stepDesc.setText(step.getDesc());
        holder.stepPosition.setText("Этап " + Integer.toString(holder.getAdapterPosition() + 1));

        // Таймер и все, что с ним связано (пауза, старт, перезапуск)
        if (step.getHour() != 0 || step.getMin() !=0 || step.getSec() !=0) {

            holder.START_TIME = step.getHour() * 3600 * 1000 + step.getMin() * 60 * 1000 + step.getSec() * 1000;
            holder.timerTimeLeft = holder.START_TIME;

            holder.timeLeft.setVisibility(View.VISIBLE);
            holder.timerStartStop.setVisibility(View.VISIBLE);
            holder.timerReset.setVisibility(View.INVISIBLE);

            holder.timerStartStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.isTimerRunning) {
                        holder.countDownTimer.cancel();
                        holder.isTimerRunning = false;
                        holder.timerStartStop.setText("начать");
                        holder.timerReset.setVisibility(View.VISIBLE);
                    }else {
                        holder.countDownTimer = new CountDownTimer(holder.timerTimeLeft, 1000) {
                            @Override
                            public void onTick(long l) {
                                holder.timerTimeLeft = l;
                                int hours = (int) holder.timerTimeLeft / 1000 / 3600;
                                int minitues = (int) holder.timerTimeLeft / 1000 / 60 % 60;
                                int sec = (int) holder.timerTimeLeft / 1000 % 60;
                                String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
                                holder.timeLeft.setText(timeLeftString);
                            }

                            @Override
                            public void onFinish() {
                                holder.isTimerRunning = false;
                                holder.timerStartStop.setText("начать");
                                holder.timerStartStop.setVisibility(View.INVISIBLE);
                                holder.timerReset.setVisibility(View.VISIBLE);
                                mediaPlayer = MediaPlayer.create(inflater.getContext(), R.raw.timer);
                                mediaPlayer.start();
                                mediaPlayer.setLooping(true);
                                AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                                builder.setTitle("Таймер")
                                        .setMessage("Таймер этапа " + Integer.toString(holder.getAdapterPosition() + 1) + " закончил свою работу")
                                        .setPositiveButton("Понял", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                mediaPlayer.stop();
                                                dialogInterface.cancel();
                                            }
                                        });
                                builder.show();


                            }
                        }.start();
                        holder.isTimerRunning = true;
                        holder.timerStartStop.setText("пауза");
                        holder.timerReset.setVisibility(View.INVISIBLE);
                    }
                }
            });

            holder.timerReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.timerTimeLeft = holder.START_TIME;
                    int hours = (int) holder.timerTimeLeft / 1000 / 3600;
                    int minitues = (int) holder.timerTimeLeft / 1000 / 60 % 60;
                    int sec = (int) holder.timerTimeLeft / 1000 % 60;
                    String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
                    holder.timeLeft.setText(timeLeftString);
                    holder.timerReset.setVisibility(View.INVISIBLE);
                    holder.timerStartStop.setVisibility(View.VISIBLE);
                }
            });
            int hours = (int) holder.timerTimeLeft / 1000 / 3600;
            int minitues = (int) holder.timerTimeLeft / 1000 / 60 % 60;
            int sec = (int) holder.timerTimeLeft / 1000 % 60;
            String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
            holder.timeLeft.setText(timeLeftString);
        }
    }

    // Получить количество элементов в адаптере
    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepDesc, timeLeft, stepPosition;
        boolean isTimerRunning = false;
        Button timerStartStop, timerReset;
        CountDownTimer countDownTimer;
        long START_TIME;
        long timerTimeLeft;
        ViewHolder(View view){
            super(view);
            stepPosition = view.findViewById(R.id.openRecipeStepPosition);
            stepDesc = view.findViewById(R.id.openRecipeStepDesc);
            timeLeft = view.findViewById(R.id.timerTimeLeft);
            timerReset = view.findViewById(R.id.timerResetTimer);
            timerStartStop = view.findViewById(R.id.timerStartTimer);
        }
    }

}

