package com.pavlovic.cubes.events24.ui.activity.registration;

import android.os.Bundle;
import android.view.View;

import com.pavlovic.cubes.events24.ui.view.EventsMessage;
import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.databinding.ActivityForgotPasswordBinding;
import com.pavlovic.cubes.events24.ui.activity.BasicActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends BasicActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.editTextMail.getText().toString();

                if(email.length() > 0){
                    auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            EventsMessage.showMessage(getApplicationContext(),R.string.text_check_email);
                        }
                    });
                }

            }
        });

    }
}