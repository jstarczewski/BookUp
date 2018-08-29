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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017-06-27.
 */

public class RVBookAdapter extends RecyclerView.Adapter<RVBookAdapter.BookHolder> {


    Context context;
    SharedPreferences sharedPreferences;
    CardView cardViewBook;
    int kk;
    int jj;

    public static class BookHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView titel;
        TextView author;
        TextView info;
        EditText eTextTitel;
        EditText eTextAuthor;
        EditText eTextNote;
        ImageButton imagebutton;
        ImageView imageView;

        public BookHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.bookCardView);
            titel = (TextView)itemView.findViewById(R.id.titel);
            author = (TextView)itemView.findViewById(R.id.author);
            info = (TextView)itemView.findViewById(R.id.info);

            eTextTitel = (EditText)itemView.findViewById(R.id.editTextTitel);
            eTextAuthor = (EditText)itemView.findViewById(R.id.editTextAuthor);
            eTextNote = (EditText)itemView.findViewById(R.id.editTextNote);
            imagebutton = (ImageButton)itemView.findViewById(R.id.imageButton);
            imageView = (ImageView)itemView.findViewById(R.id.imageView4);

            imagebutton.setVisibility(View.GONE);

        }
    }
    private List<Book> books = new ArrayList<>();
    RVBookAdapter(List<Book> books){
        this.books = books;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {


        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RVBookAdapter.BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);

        final BookHolder bookHolder = new BookHolder(view);



        context = view.getContext();
        sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        cardViewBook = bookHolder.cardView;
        final MenuActivity menuActivity = (MenuActivity)context;


        bookHolder.imagebutton.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("shrinkBooks", false)) {
            bookHolder.imageView.setVisibility(View.VISIBLE);
            bookHolder.info.setVisibility(View.GONE);
        }
        else {bookHolder.imageView.setVisibility(View.GONE);}

        bookHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (bookHolder.eTextNote.getVisibility()==View.VISIBLE) {}

                else {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("number", bookHolder.getAdapterPosition());
                    sharedPreferences.edit().putString("bookTitelNew", bookHolder.titel.getText().toString()).apply();




                Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                menuActivity.floatingActionButton.startAnimation(fadeOut);
                menuActivity.floatingActionButton.setVisibility(View.INVISIBLE);

                menuActivity.startActivity(intent);
              menuActivity.overridePendingTransition(R.animator.translate_enter, R.animator.translate_leave);}

            }
        });
        bookHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if(bookHolder.titel.getVisibility()==View.VISIBLE) {

                    bookHolder.titel.setVisibility(View.GONE);
                    bookHolder.author.setVisibility(View.GONE);
                    bookHolder.info.setVisibility(View.GONE);
                    menuActivity.holdScreen();


                    if (sharedPreferences.getBoolean("shrinkBooks", false)) {
                        bookHolder.imageView.setVisibility(View.GONE);}




                    bookHolder.eTextTitel.setVisibility(View.VISIBLE);
                    bookHolder.eTextAuthor.setVisibility(View.VISIBLE);
                    bookHolder.eTextNote.setVisibility(View.VISIBLE);
                    bookHolder.imagebutton.setVisibility(View.VISIBLE);




                    for (int i =0; i<=bookHolder.getAdapterPosition(); i++) {
                        jj++;
                    }

                    bookHolder.eTextTitel.setText(sharedPreferences.getString("titelBook"+jj, null));
                    bookHolder.eTextAuthor.setText(sharedPreferences.getString("authorBook"+jj, null));
                    bookHolder.eTextNote.setText(sharedPreferences.getString("infoBook"+jj, null));
                    jj=0;



                }
                else {
                    bookHolder.titel.setVisibility(View.VISIBLE);
                    bookHolder.author.setVisibility(View.VISIBLE);
                    bookHolder.info.setVisibility(View.VISIBLE);

                    menuActivity.releaseScreen();
                    if (sharedPreferences.getBoolean("shrinkBooks", false)) {
                        bookHolder.imageView.setVisibility(View.VISIBLE);}


                    for (int i = 0; i<=bookHolder.getAdapterPosition(); i++) {
                        kk++;
                    }
                    sharedPreferences.edit().putString("titelBook"+kk, bookHolder.eTextTitel.getText().toString()).apply();
                    sharedPreferences.edit().putString("authorBook"+kk, bookHolder.eTextAuthor.getText().toString()).apply();
                    sharedPreferences.edit().putString("infoBook"+kk, bookHolder.eTextNote.getText().toString()).apply();

                    kk=0;
                    bookHolder.titel.setText(bookHolder.eTextTitel.getText().toString());
                    bookHolder.author.setText(bookHolder.eTextAuthor.getText().toString());
                    bookHolder.info.setText(bookHolder.eTextNote.getText().toString());

                    bookHolder.eTextTitel.setVisibility(View.GONE);
                    bookHolder.eTextAuthor.setVisibility(View.GONE);
                    bookHolder.eTextNote.setVisibility(View.GONE);
                    bookHolder.imagebutton.setVisibility(View.GONE);



                }


                return true;
            }
        });

        bookHolder.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                books.remove(bookHolder.getAdapterPosition());
                for (int i = bookHolder.getAdapterPosition(); i<=books.size();i++) {
                    sharedPreferences.edit().putString("titelBook" + (i + 1), sharedPreferences.getString("titelBook" + (i + 2), null)).apply();
                    sharedPreferences.edit().putString("authorBook" + (i + 1), sharedPreferences.getString("authorBook" + (i + 2), null)).apply();
                    sharedPreferences.edit().putString("infoBook" + (i + 1), sharedPreferences.getString("infoBook" + (i + 2), null)).apply();

                    sharedPreferences.edit().putInt("pages"+(i), sharedPreferences.getInt("pages"+(i+1), 0)).apply();
                    sharedPreferences.edit().putInt("pagesNum"+(i), sharedPreferences.getInt("pagesNum"+(i+1),0)).apply();


                    SharedPreferences sharedPreferences1 = context.getSharedPreferences("prefs" + i, Context.MODE_PRIVATE);
                    SharedPreferences sharedPreferences2 = context.getSharedPreferences("prefs" + (i + 1), Context.MODE_PRIVATE);
                    sharedPreferences.edit().putInt("kant" + (i), sharedPreferences.getInt("kant" + (i + 1), 0)).apply();

                    sharedPreferences1.edit().clear().apply();
                    for (int b = 0; b <= sharedPreferences.getInt("kant" + (i), 0); b++) {

                        sharedPreferences1.edit().putString("titel" + (b), sharedPreferences2.getString("titel" + (b), null)).apply();
                        sharedPreferences1.edit().putString("note" + (b), sharedPreferences2.getString("note" + (b), null)).apply();


                    }
                    sharedPreferences2.edit().clear().apply();




                }


                notifyItemRemoved(bookHolder.getAdapterPosition());
                int count = sharedPreferences.getInt("cant", 0);
                count--;
                sharedPreferences.edit().putInt("cant", count).apply();




            }
        });

        bookHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation fade = AnimationUtils.loadAnimation(context, R.anim.fade_out_long);

                Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in_long);

                if (bookHolder.info.getVisibility()==View.GONE) {
                    bookHolder.info.startAnimation(fadeIn);
                bookHolder.info.setVisibility(View.VISIBLE);}
                else {bookHolder.info.startAnimation(fade);
                    bookHolder.info.setVisibility(View.GONE);}



            }
        });


        return bookHolder;
    }

    @Override
    public void onBindViewHolder(BookHolder bookHolder, int position) {
            bookHolder.author.setText(books.get(position).bookAuthor);
            bookHolder.titel.setText(books.get(position).bookTitel);
            bookHolder.info.setText(books.get(position).bookInfo);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
