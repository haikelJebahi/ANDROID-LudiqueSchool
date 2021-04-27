package com.example.ludiqueschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PageChoixDifficulte extends AppCompatActivity { //fonctionne uniquement pour les maths mais elle est générique si améliorations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_choix_difficulte);
    }

    public void niveau1BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            //intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, Calcul.ADDITION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau2BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            //intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, Calcul.SOUSTRACTION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau3BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            //intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, Calcul.MULTIPLICATION);
            startActivity(intentPageExercicesMaths);
    }

    public void niveau4BTN(View view) {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
           // intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, Calcul.DIVISION);
            startActivity(intentPageExercicesMaths);
    }
}
