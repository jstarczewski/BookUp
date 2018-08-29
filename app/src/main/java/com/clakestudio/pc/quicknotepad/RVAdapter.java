package com.clakestudio.pc.quicknotepad;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by pc on 2017-06-17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardNoteHolder> {
    Context mContext;
    SharedPreferences sharedPreferences;
    CardView cardView;
    CardNoteHolder cardNoteHolder;
    int kk=0;
    int jj=0;
    public static class CardNoteHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titel;
        TextView note;
        EditText editTextTitel;
        EditText editTextNote;
        ImageButton imageButton;
        ImageView imageView;

        CardNoteHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            titel = (TextView) itemView.findViewById(R.id.titel);
            note = (TextView) itemView.findViewById(R.id.textX);
            editTextNote =(EditText)itemView.findViewById(R.id.eTextNOte);
            editTextTitel = (EditText)itemView.findViewById(R.id.eTextTitel);
            imageButton = (ImageButton)itemView.findViewById(R.id.imageButton);
            imageView = (ImageView)itemView.findViewById(R.id.imageView3);


        }
    }
    private List<CardNote> cardNotes = new ArrayList<>();
    RVAdapter(List<CardNote> cardNotes) {
        this.cardNotes = cardNotes;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public CardNoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
          cardNoteHolder  = new CardNoteHolder(view);
        mContext = view.getContext();

        return cardNoteHolder;
    }


    @Override
    public void onBindViewHolder(final CardNoteHolder cardNoteHolder, int position) {
            cardNoteHolder.titel.setText(cardNotes.get(position).titel);
            cardNoteHolder.note.setText(cardNotes.get(position).note);



        sharedPreferences=mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final int direct = sharedPreferences.getInt("direct", 0);
        final SharedPreferences sharedPreferences1 = mContext.getSharedPreferences("prefs"+direct, Context.MODE_PRIVATE);

        final MainActivity mainActivity = (MainActivity)mContext;


        if (sharedPreferences.getBoolean("vis", false)) {
            cardNoteHolder.cv.callOnClick();
            sharedPreferences.edit().putBoolean("vis", false).apply();
        }

        if (sharedPreferences.getBoolean("shrinkNotes", false)) {
            cardNoteHolder.note.setVisibility(View.GONE);
            cardNoteHolder.imageView.setVisibility(View.VISIBLE);
        }
        else {
            cardNoteHolder.note.setVisibility(View.VISIBLE);
            cardNoteHolder.imageView.setVisibility(View.GONE);
        }
        cardNoteHolder.imageButton.setVisibility(View.GONE);
        cardNoteHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (cardNoteHolder.titel.getVisibility() == View.VISIBLE) {
                    mainActivity.holdScreen();

                    cardNoteHolder.titel.setVisibility(View.GONE);
                    cardNoteHolder.note.setVisibility(View.GONE);
                    cardNoteHolder.editTextNote.setVisibility(View.VISIBLE);
                    cardNoteHolder.editTextTitel.setVisibility(View.VISIBLE);
                    cardNoteHolder.imageButton.setVisibility(View.VISIBLE);
                    if (sharedPreferences.getBoolean("shrinkNotes", false)) {
                        cardNoteHolder.imageView.setVisibility(View.GONE);
                    }
                    else {
                        cardNoteHolder.imageView.setVisibility(View.GONE);
                    }
                    for (int i = 0; i<=cardNoteHolder.getAdapterPosition();i++) {
                        kk++;
                    }
                    cardNoteHolder.editTextTitel.setText(sharedPreferences1.getString("titel"+kk, null));
                    cardNoteHolder.editTextNote.setText(sharedPreferences1.getString("note"+kk, null));
                    kk=0;




                } else {


                    cardNoteHolder.titel.setText(cardNoteHolder.editTextTitel.getText().toString());
                    cardNoteHolder.note.setText(cardNoteHolder.editTextNote.getText().toString());
                    cardNoteHolder.titel.setVisibility(View.VISIBLE);
                    cardNoteHolder.note.setVisibility(View.VISIBLE);

                    for (int i = 0; i<=cardNoteHolder.getAdapterPosition(); i++) {
                        jj++;
                    }
                    sharedPreferences1.edit().putString("titel" + jj, cardNoteHolder.titel.getText().toString()).apply();
                    sharedPreferences1.edit().putString("note" + jj, cardNoteHolder.note.getText().toString()).apply();
                    jj=0;

                    cardNoteHolder.editTextNote.setVisibility(View.GONE);
                    cardNoteHolder.editTextTitel.setVisibility(View.GONE);
                    cardNoteHolder.imageButton.setVisibility(View.GONE);
                    if (sharedPreferences.getBoolean("shrinkNotes", false)) {
                        cardNoteHolder.imageView.setVisibility(View.VISIBLE);
                    }
                    else {
                        cardNoteHolder.imageView.setVisibility(View.GONE);
                    }
                    mainActivity.releaseScreen();

                }

            }
        });
        cardNoteHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                sharedPreferences.edit().putInt("numberInt", cardNoteHolder.getAdapterPosition()).apply();
                final MainActivity mainActivity = (MainActivity)mContext;
                Intent intent = new Intent(mContext, PermissionActivity.class);
                mainActivity.startActivity(intent);

                return true;
            }
        });

        cardNoteHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                cardNotes.remove(cardNoteHolder.getAdapterPosition());
                for (int i = cardNoteHolder.getAdapterPosition(); i<=cardNotes.size();i++) {
                    sharedPreferences1.edit().putString("titel"+(i+1), sharedPreferences1.getString("titel"+(i+2), null)).apply();
                    sharedPreferences1.edit().putString("note"+(i+1), sharedPreferences1.getString("note"+(i+2), null)).apply();


                }
                notifyItemRemoved(cardNoteHolder.getAdapterPosition());
                int count = sharedPreferences.getInt("kant"+direct, 0);
                count--;
                sharedPreferences.edit().putInt("kant"+direct, count).apply();



            }
        });

        cardNoteHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fade = AnimationUtils.loadAnimation(mContext, R.anim.fade_out_long);

                Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_long);
                if (cardNoteHolder.note.getVisibility()==View.VISIBLE) {
                    cardNoteHolder.note.startAnimation(fade);
                    cardNoteHolder.note.setVisibility(View.GONE);
                }

                else {
                    cardNoteHolder.note.startAnimation(fadeIn);
                    cardNoteHolder.note.setVisibility(View.VISIBLE);}

            }
        });

    }
    @Override
    public int getItemCount() {
        return cardNotes.size();
    }

}
