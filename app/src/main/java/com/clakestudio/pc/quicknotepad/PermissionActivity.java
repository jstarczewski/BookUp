package com.clakestudio.pc.quicknotepad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by pc on 2017-07-10.
 */

public class PermissionActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);


        Button button = (Button)findViewById(R.id.button3);


        if (sharedPreferences.getBoolean("permissionCheck", true)) {
            Intent intentMain = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(intentMain);
            finish();
        }
        else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    sharedPreferences.edit().putBoolean("permissionCheck", true).apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    } else {
                        Intent intentMain = new Intent(getApplicationContext(), CameraActivity.class);
                        startActivity(intentMain);
                        finish();
                    }

                }
            });

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, CameraActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Permission not Granted :(", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return;
        }

        }

    }







