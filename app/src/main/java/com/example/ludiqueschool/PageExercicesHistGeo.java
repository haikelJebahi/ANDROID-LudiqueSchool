package com.example.ludiqueschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PageExercicesHistGeo extends AppCompatActivity {
    private TextView titre;
    public static final String CATEGORIE_KEY= "categorie_key";//categorie du QCM (Histoire, Géo)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_exercices_hist_geo);
        titre = findViewById(R.id.titre);
        String choixCategorie = getIntent().getStringExtra(CATEGORIE_KEY);

        if(choixCategorie.equals(PageMenu.HISTOIRE)){
            titre.setText(getText(R.string.histoireexo));
        } else if (choixCategorie.equals(PageMenu.GEOGRAPHIE)){
            titre.setText(getText(R.string.geoexo));
            }
        }
    }
