package com.pavlovic.cubes.events24.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pavlovic.cubes.events24.data.SharedPrefs;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pavlovic.cubes.events24.ui.activity.BasicActivity;
import com.pavlovic.cubes.events24.ui.activity.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pavlovic.cubes.events24.databinding.ActivityLoginBinding;

public class LoginActivity extends BasicActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.editTextMail.setText(SharedPrefs.getInstance(this).getEmail());

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do login

                String email = binding.editTextMail.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if(email.length()>0 && password.length()>0) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        SharedPrefs.getInstance(LoginActivity.this).setNotificationStatus(true);
                                        FirebaseMessaging.getInstance().subscribeToTopic("general");

                                        finish();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        binding.buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        binding.buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });



    }
}