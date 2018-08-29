package com.clakestudio.pc.quicknotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pc on 2017-07-10.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent MenuActivity = new Intent(this, MenuActivity.class);
        startActivity(MenuActivity);
        finish();

    }
}

