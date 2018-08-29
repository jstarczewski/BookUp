package com.clakestudio.pc.quicknotepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017-06-27.
 */

public class MenuActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int cant;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<Book> books;


    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Books");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));



         floatingActionButton = (FloatingActionButton)findViewById(R.id.fabMenu);
        floatingActionButton.setVisibility(View.INVISIBLE);
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        floatingActionButton.startAnimation(fadeIn);
        floatingActionButton.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerBook);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        final boolean visited = sharedPreferences.getBoolean("has_visited", false);



        ArrayList<Book> books = new ArrayList<>();
        final RVBookAdapter rvBookAdapter = new RVBookAdapter(books);
        recyclerView.setAdapter(rvBookAdapter);


        cant = sharedPreferences.getInt("cant", 0);
        onBookAdded();


        if (!sharedPreferences.getBoolean("dialogMenu", false)) {
            DialogMenu dialogMenu = new DialogMenu();
            dialogMenu.show(getFragmentManager(), "tag");
            sharedPreferences.edit().putBoolean("dialogMenu", true).apply();}

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!visited) {
                    Toast.makeText(getApplicationContext(), "longClick on note to edit, longClick again to save notes, tap to delve into",
                            Toast.LENGTH_LONG).show();
                    sharedPreferences.edit().putBoolean("has_visited", true).apply();
                }

                cant = sharedPreferences.getInt("cant", 0);
                cant++;
                onBookAdded();
                sharedPreferences.edit().putInt("cant", cant).apply();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case  R.id.more:
                AboutIntent();
                return true;

            default:


                return super.onOptionsItemSelected(item);


        }



    }
    public void AboutIntent() {
        Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        floatingActionButton.startAnimation(fadeOut);
        floatingActionButton.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, ActivityInfo.class);
        startActivity(intent);
        overridePendingTransition(R.animator.translate_enter_reverse, R.animator.translate_leave_reverse);

    }

    public void onBookAdded() {

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        books = new ArrayList<>();
        RVBookAdapter rvBookAdapter = new RVBookAdapter(books);
        recyclerView.setAdapter(rvBookAdapter);
        for (int i = 1; i<=cant; i++) {
            books.add(new Book(sharedPreferences.getString("titelBook"+i, null), sharedPreferences.getString("authorBook"+i, null), sharedPreferences.getString("infoBook"+i, null)));
        }

    }

    @Override
    public void onBackPressed() {

    }
    public void holdScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    public void releaseScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
