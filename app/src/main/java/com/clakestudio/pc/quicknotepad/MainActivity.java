package com.clakestudio.pc.quicknotepad;
import android.app.*;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity{


    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    TextView titelTExt;
    TextView text;
    EditText eTextTitel;
    EditText editText;
    Button button;
    TextView textViewW;
    SeekBar seekBar;
    private long LastPause;
    boolean isPause;

     ImageView imageView;

    Chronometer chronometer;
    EditText eTextNote;
    private List<CardNote> cardNotes;
    private RecyclerView recyclerView;
    int kant;
    boolean run;
    int direct;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        toolbar.setTitle(sharedPreferences.getString("bookTitelNew", "Notes"));


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);


        run = false;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AboutIntent();



            }
        });

    }
    @Override
    protected void onStart() {
        onRestart();
        super.onStart();



        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.setVisibility(View.INVISIBLE);
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        floatingActionButton.startAnimation(fadeIn);
        floatingActionButton.setVisibility(View.VISIBLE);




        titelTExt = (TextView)findViewById(R.id.titel);
        text = (TextView)findViewById(R.id.textX);
        eTextTitel = (EditText)findViewById(R.id.eTextTitel);
        eTextNote = (EditText)findViewById(R.id.eTextNOte);

        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        cardNotes = new ArrayList<>();
        final  RVAdapter rvAdapter = new RVAdapter(cardNotes);
        recyclerView.setAdapter(rvAdapter);




        Intent intent = getIntent();
        int number = intent.getExtras().getInt("number");
        sharedPreferences.edit().putInt("direct", number).apply();
        direct = sharedPreferences.getInt("direct",0);
        kant = sharedPreferences.getInt("kant"+direct, 0);
        onStartNotes();


        if (!sharedPreferences.getBoolean("dialog", false)) {
            com.clakestudio.pc.quicknotepad.Dialog dialog = new com.clakestudio.pc.quicknotepad.Dialog();
        dialog.show(getFragmentManager(), "tag");
        sharedPreferences.edit().putBoolean("dialog", true).apply();}



        seekBarInfo();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kant = sharedPreferences.getInt("kant"+direct, 0);
                kant++;
                onStartNotes();
                sharedPreferences.edit().putInt("kant"+direct, kant).apply();

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

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.translate_enter_reverse, R.animator.translate_leave_reverse);
        this.finish();
    }
    public void onStartNotes() {


        final SharedPreferences sharedPreferences1 = getSharedPreferences("prefs" + direct, Context.MODE_PRIVATE);
        cardNotes = new ArrayList<>();
        final RVAdapter rvAdapter = new RVAdapter(cardNotes);
        recyclerView.setAdapter(rvAdapter);
        for (int i = 1; i <= kant; i++) {
            cardNotes.add(new CardNote(sharedPreferences1.getString("titel" + i, null), sharedPreferences1.getString("note" + i, null)));
        }



    }


    @Override
    public void onBackPressed() {
        AboutIntent();
        releaseScreen();

    }
    public void holdScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    public void releaseScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    public void seekBarInfo() {

        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button2);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        textViewW = (TextView)findViewById(R.id.textView4);
        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);


        if (sharedPreferences.getBoolean("pagesB"+direct, false)) {
            seekBar.setMax(sharedPreferences.getInt("pages"+direct, 0));
            seekBar.setProgress(sharedPreferences.getInt("pagesNum"+direct, 0));
            textViewW.setText(sharedPreferences.getInt("pagesNum"+direct,0)+"/"+sharedPreferences.getInt("pages"+direct,0));

            button.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            seekBar.setVisibility(View.VISIBLE);
            textViewW.setVisibility(View.VISIBLE);
            textViewW.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    sharedPreferences.edit().putBoolean("pagesB"+direct, false).apply();
                    seekBarInfo();


                    return true;
                }
            });


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    sharedPreferences.edit().putInt("pagesNum"+direct, i).apply();
                    seekBar.setProgress(i);
                    textViewW.setText(sharedPreferences.getInt("pagesNum"+direct,0)+"/"+sharedPreferences.getInt("pages"+direct,0));

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        else {
            button.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.GONE);
            textViewW.setVisibility(View.GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(editText.getText().toString().length()>0) {
                        sharedPreferences.edit().putBoolean("pagesB"+direct, true).apply();
                        sharedPreferences.edit().putInt("pages"+direct, Integer.parseInt(editText.getText().toString())).apply();
                        seekBar.setMax(Integer.parseInt(editText.getText().toString()));
                        button.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                        seekBar.setVisibility(View.VISIBLE);
                        textViewW.setVisibility(View.VISIBLE);
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                                sharedPreferences.edit().putInt("pagesNum"+direct, i).apply();
                                seekBar.setProgress(i);
                                textViewW.setText(sharedPreferences.getInt("pagesNum"+direct,0)+"/"+sharedPreferences.getInt("pages"+direct,0));

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                    }

                }
            });
        }
    }

}
