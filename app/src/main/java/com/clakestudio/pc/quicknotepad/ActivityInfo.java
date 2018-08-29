package com.clakestudio.pc.quicknotepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by pc on 2017-07-02.
 */

public class ActivityInfo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBoxNotes);
        final CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkBoxBooks);


        if (sharedPreferences.getBoolean("shrinkNotes", false)) {
            checkBox.setChecked(true);
        }
        if (sharedPreferences.getBoolean("shrinkBooks", false)) {
            checkBox1.setChecked(true);
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (checkBox.isChecked()) {
                    sharedPreferences.edit().putBoolean("shrinkNotes", true).apply();
                }
                else {
                    sharedPreferences.edit().putBoolean("shrinkNotes", false).apply();
                }

            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox1.isChecked()) {
                    sharedPreferences.edit().putBoolean("shrinkBooks", true).apply();
                }
                else {
                    sharedPreferences.edit().putBoolean("shrinkBooks", false).apply();
                }
            }
        });


    }
    public void back(View view) {
        IntentBack();



    }
    public void IntentBack() {

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
        overridePendingTransition(R.animator.translate_enter, R.animator.translate_leave);
        this.finish();
    }
}
