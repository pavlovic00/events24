package com.pavlovic.cubes.events24.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pavlovic.cubes.events24.tools.LanguageTools;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageTools.setLanguageResourceConfiguration(this);
    }
}
