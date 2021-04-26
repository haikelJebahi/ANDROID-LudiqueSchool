package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PageMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_menu);
    }

    public void mathBTN(View view) {
        Intent intentPageChoixDifficulte = new Intent(this, PageChoixDifficulte.class);
       // intentPageChoixDifficulte.putExtra(PageChoixDifficulte.TYPE_KEY,"Mathématiques");
        startActivity(intentPageChoixDifficulte);
    }

    public void histoireBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
        //intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY, Question.getHISTOIRE());
        startActivity(intentPageExercicesHistGeo);
    }

    public void geographieBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
       // intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY,Question.getGEOGRAPHIE());
        startActivity(intentPageExercicesHistGeo);
    }
}