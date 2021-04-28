package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class PageExercicesHistGeo extends AppCompatActivity {
    private TextView titre;
    public static final String CATEGORIE_KEY= "categorie_key";//categorie du QCM (Histoire, Géo)
    private TextView compteur, enonce,erreur;
    private RadioButton rep1,rep2,rep3;
    private String choixCategorie;
    private int index,point,nbQuestions;
    private Button suivantBTN, precedentBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_exercices_hist_geo);

        titre = findViewById(R.id.titre);
        choixCategorie = getIntent().getStringExtra(CATEGORIE_KEY);

        Log.d("choix",choixCategorie);
        if(choixCategorie.equals(PageMenu.HISTOIRE))
        {
            titre.setText(getText(R.string.histoireexo));
        } else if (choixCategorie.equals(PageMenu.GEOGRAPHIE))
        {
            titre.setText(getText(R.string.geoexo));
        }
        rep1 = findViewById(R.id.rep1);
        rep2 = findViewById(R.id.rep1);
        rep3 = findViewById(R.id.rep1);
        compteur = findViewById(R.id.compteur);
        enonce = findViewById(R.id.enonce);
        index = 0;
        point =0;
        nbQuestions = 10;
        suivantBTN = findViewById(R.id.suivantBTN);
        precedentBTN = findViewById(R.id.precedentBTN);
        compteur = (TextView) findViewById(R.id.compteur);
        erreur = (TextView) findViewById(R.id.erreur);
        compteur.setText((index+1)+"/"+nbQuestions);
        //actualiserPage();*/
    }

    public void lancerBTN(View view)
    {
        if(suivantBTN.getText().equals("understand"))
        {
            enonce.setText("");
            rep1.setVisibility(View.VISIBLE);
            rep2.setVisibility(View.VISIBLE);
            rep3.setVisibility(View.VISIBLE);
            compteur.setText((index+1)+"/"+nbQuestions);
            precedentBTN.setVisibility(View.VISIBLE);
            suivantBTN.setText("confirm");
        }
        if(suivantBTN.getText().equals("confirm"))
        {

        }

    }

    public void precedentBTN(View view) {
        precedentBTN.setVisibility(View.INVISIBLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(PageExercicesHistGeo.this);
        builder.setTitle("Attention !");
        builder.setMessage("Si vous quittez l'exercice toute progression sera perdue");
        builder.setPositiveButton("D'accord, quitter maintenant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compteur.setText("");
                finish();
                Intent intentPageMeu = new Intent(PageExercicesHistGeo.this,PageMenu.class);
                startActivity(intentPageMeu);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                index = 0;
                precedentBTN.setVisibility(View.VISIBLE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}
