package com.example.foodhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText registerUsername, registerEmail, registerPassword;
    private Button registerButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = registerEmail.getText().toString().trim();
        String username = registerUsername.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if (username.isEmpty()) {
            registerUsername.setError("Напишите тут свой псевдоним");
            registerUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            registerEmail.setError("Напишите тут свою почту");
            registerEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            registerPassword.setError("Напишите тут свой пароль");
            registerPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("Укажите корректную почту");
            registerEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            registerPassword.setError("Пароль должен быть бдлинее 6 символов");
            registerPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Произошла ошибка. Попробуйте еще раз", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}