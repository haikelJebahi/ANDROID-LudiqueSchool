package com.example.ludiqueschool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PageResultat extends AppCompatActivity
{
    public static final String BONNE_REPONSE ="bonne reponse";
    private TextView texte;
    private String point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_resultat);

        texte = (TextView) findViewById(R.id.texte);
        point =  getIntent().getStringExtra(BONNE_REPONSE);

        texte.setText(point);

    }
}
