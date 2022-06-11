package com.pavlovic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pavlovic.cubes.events24.ui.activity.registration.MainRegistrationActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.pavlovic.cubes.events24.databinding.ActivitySplashscreenBinding;

public class SplashscreenActivity extends BasicActivity {

    private ActivitySplashscreenBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainRegistrationActivity.class));
        }
        else{
            finish();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("EVENTS_NOTIFICATION", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Toast.makeText(SplashscreenActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}