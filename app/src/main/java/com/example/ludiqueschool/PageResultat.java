package com.example.ludiqueschool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PageResultat extends AppCompatActivity
{
    public static final String BONNE_REPONSE ="bonne reponse";
    private TextView note,commentaire;
    private String point,choix;
    private int nbP;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_resultat);

        commentaire = (TextView) findViewById(R.id.commentaire);
        note = (TextView) findViewById(R.id.note);

        list=getIntent().getExtras().getStringArrayList(BONNE_REPONSE);
        point =  list.get(0);
        choix = list.get(1);

        note.setText(point+"/10");
        nbP = Integer.valueOf(point);

        if(nbP == 0)
        {
            note.setTextColor(RED);
            commentaire.setText("Il faut travailler d'avantage !!!");
        }
        else if(nbP<5)
        {
            note.setTextColor(RED);
            commentaire.setText("Insuffisant pour ton niveau !!!");
        }
        else if(nbP == 5)
        {
            note.setTextColor(GREEN);
            commentaire.setText("La moyenne n'est pas suffisant pour être le meilleur !!!");
        }
        else if(nbP >5)
        {
            note.setTextColor(GREEN);
            commentaire.setText("Tu peux faire encore mieux !!!");
        }
        else if(nbP == 10)
        {
            note.setTextColor(GREEN);
            commentaire.setText("Félicitation, tu ne peux pas faire mieux !!!");
        }
    }

    public void retourBTN(View view) {
        finish();
        Intent intentPageMenu = new Intent(this, PageMenu.class);
        startActivity(intentPageMenu);
    }

    public void recommencerBTN(View view) {
        finish();
        Log.d("choix",choix);
        Log.d("hist-geo",PageMenu.HISTOIRE);
        if(choix.equals(PageMenu.HISTOIRE) || choix.equals(PageMenu.GEOGRAPHIE))
        {
            Log.d("AHAZAAAAA-geo","AHAZAAAAA-geo");
            Intent intentPageHistGeo = new Intent(this, PageExercicesHistGeo.class);
            intentPageHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY, choix);
            startActivity(intentPageHistGeo);
        }
        else {
            Log.d("FAUXXXXXXX-geo","AHAZAAAAA-geo");
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, choix);
            startActivity(intentPageExercicesMaths);
        }
    }
}
