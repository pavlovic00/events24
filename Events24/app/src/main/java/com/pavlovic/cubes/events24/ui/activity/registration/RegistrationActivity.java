package com.pavlovic.cubes.events24.ui.activity.registration;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pavlovic.cubes.events24.data.model.User;
import com.pavlovic.cubes.events24.ui.view.EventsMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pavlovic.cubes.events24.ui.activity.BasicActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pavlovic.cubes.events24.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends BasicActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        binding.buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.editTextMail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                String passwordRepeat = binding.editTextRepeatPassword.getText().toString();

                if(email.length()>0 && passwordRepeat.equalsIgnoreCase(password) && password.length()>0){

                    updateLoadingUI(true);

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(task.getResult().getUser().getUid(),
                                        binding.editTextName.getText().toString(),
                                        binding.editTextSurname.getText().toString(),email);

                                FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        EventsMessage.showMessage(getApplicationContext(),"Registration complete!");
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    }
                                });
                            }
                            else{
                               updateLoadingUI(false);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            updateLoadingUI(false);
                            EventsMessage.showMessage(getApplicationContext(),e.getLocalizedMessage());
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    private void updateLoadingUI(boolean isLoading){
        binding.editTextPassword.setEnabled(!isLoading);
        binding.editTextRepeatPassword.setEnabled(!isLoading);
        binding.editTextMail.setEnabled(!isLoading);
        binding.buttonForgotPassword.setEnabled(!isLoading);
        binding.buttonLogin.setEnabled(!isLoading);

        if(isLoading){
            binding.buttonRegistration.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.buttonRegistration.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }

    }
}