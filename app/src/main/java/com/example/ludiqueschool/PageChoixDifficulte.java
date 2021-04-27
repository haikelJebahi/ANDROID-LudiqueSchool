package com.example.ludiqueschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PageChoixDifficulte extends AppCompatActivity
{ //fonctionne uniquement pour les maths mais elle est générique si améliorations

    public static final String ADDITION = "+";
    public static final String SOUSTRACTION = "-";
    public static final String DIVISION = "/";
    public static final String MULTIPLICATION = "*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_choix_difficulte);
    }

    public void niveau1BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, ADDITION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau2BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, SOUSTRACTION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau3BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, MULTIPLICATION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau4BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, DIVISION);
            startActivity(intentPageExercicesMaths);
    }
}
