package com.example.foodhub.Search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import com.example.foodhub.R;

import java.util.Locale;

public class CountDownRunnable implements Runnable{


    long START_TIME;
    long timerTimeLeft;
    MediaPlayer mediaPlayer;

    @Override
    public void run() {
        START_TIME = step.getHour() * 3600 * 1000 + step.getMin() * 60 * 1000 + step.getSec() * 1000;
        timerTimeLeft = START_TIME;

        holder.timeLeft.setVisibility(View.VISIBLE);
        holder.timerStartStop.setVisibility(View.VISIBLE);
        holder.timerReset.setVisibility(View.INVISIBLE);

        holder.timerStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning) {
                    countDownTimer.cancel();
                    isTimerRunning = false;
                    holder.timerStartStop.setText("начать");
                    holder.timerReset.setVisibility(View.VISIBLE);
                }else {
                    countDownTimer = new CountDownTimer(timerTimeLeft, 1000) {
                        @Override
                        public void onTick(long l) {
                            timerTimeLeft = l;
                            int hours = (int) timerTimeLeft / 1000 / 3600;
                            int minitues = (int) timerTimeLeft / 1000 / 60 % 60;
                            int sec = (int) timerTimeLeft / 1000 % 60;
                            String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
                            holder.timeLeft.setText(timeLeftString);
                        }

                        @Override
                        public void onFinish() {
                            isTimerRunning = false;
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
                    isTimerRunning = true;
                    holder.timerStartStop.setText("пауза");
                    holder.timerReset.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.timerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerTimeLeft = START_TIME;
                int hours = (int) timerTimeLeft / 1000 / 3600;
                int minitues = (int) timerTimeLeft / 1000 / 60 % 60;
                int sec = (int) timerTimeLeft / 1000 % 60;
                String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
                holder.timeLeft.setText(timeLeftString);
                holder.timerReset.setVisibility(View.INVISIBLE);
                holder.timerStartStop.setVisibility(View.VISIBLE);
            }
        });
        int hours = (int) timerTimeLeft / 1000 / 3600;
        int minitues = (int) timerTimeLeft / 1000 / 60 % 60;
        int sec = (int) timerTimeLeft / 1000 % 60;
        String timeLeftString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minitues, sec);
        holder.timeLeft.setText(timeLeftString);
    }
    }
}
