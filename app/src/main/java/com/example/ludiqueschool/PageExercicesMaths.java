package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class PageExercicesMaths extends AppCompatActivity {
    public static final String DIFFICULTE_KEY= "difficulte_key";//choix du niveau (+,-,*,/)
    private Button suivantBTN, precedentBTN;
    private int index, nbQuestions,point; //index = on est a quels questions
    private double resultat; // de l'operation entre val1 et val2
    int val1,val2; //valeur random (entre 0 et 99) qui change a chaque questions
    private TextView calcul, compteur,erreur;
    private String choixOperateur; //choix du niveau (+,-,*,/)
    private EditText reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages_exercices_maths);
        //initialisation

        index = 0;
        point =0;
        nbQuestions = 10;
        suivantBTN = findViewById(R.id.suivantBTN);
        precedentBTN = findViewById(R.id.precedentBTN);
        compteur = (TextView) findViewById(R.id.compteur);
        calcul = (TextView) findViewById(R.id.calcul);
        erreur = (TextView) findViewById(R.id.erreur);
        reponse = findViewById(R.id.resultat);
        choixOperateur = getIntent().getStringExtra(DIFFICULTE_KEY);
        compteur.setText((index+1)+"/"+nbQuestions);
        actualiserPage();
    }
    //ce bouton gere deux options (confirmer et suivant)

    public void suivantBTN(View view) {
        //si le bouton = confirm alors on regarde ce que l'utilisateur a ecrit pour voir si il a bon

        if(suivantBTN.getText().equals("confirm"))
            {
                //reponse vide

                if(reponse.getText().toString().equals(""))
                {
                    erreur.setTextColor(RED);
                    erreur.setText("Réponse vide !!! ");
                }
                else
                {
                    //si la reponse == resultat alors cest bon

                    double zero = resultat - Double.valueOf(reponse.getText().toString());
                    if(zero == 0)
                    {
                        erreur.setTextColor(GREEN);
                        erreur.setText("Bonne réponse !!! ");
                        point++;
                        suivantBTN.setText("Next");
                    }
                    else
                    {
                        //sinon faux

                        erreur.setTextColor(RED);
                        String faux = "Faux, la bonne réponse est "+String.valueOf(resultat);
                        erreur.setText(faux);
                        suivantBTN.setText("Next");
                    }
                }
            }
            //si le bouton = next alors on passe a la question suivante
            else {
            //si on arrive a la derniere question on ouvre la pageResultat en faisant passer une liste
            //contenant les points (note) + le choix d'exercice (+,*,/,-)
                if (index==nbQuestions-1)
                {
                    Intent intentPageResultat = new Intent(this, PageResultat.class);
                    //intentPageResultat.putExtra(PageResultat.BONNE_REPONSE, String.valueOf(point));
                    ArrayList<String> list = new ArrayList<>();
                    list.add(String.valueOf(point));
                    list.add(choixOperateur);
                    intentPageResultat.putStringArrayListExtra(PageResultat.BONNE_REPONSE,list);
                    startActivity(intentPageResultat);
                    finish();
                }
                //sinon on passe a la question suivante
                else
                {
                    erreur.setText("");
                    reponse.setText("");
                    index++;
                    suivantBTN.setText("confirm");
                    actualiserPage();
                }
            }
        }

    private void actualiserPage()
    {
        compteur.setText((index+1)+"/"+nbQuestions);
        //change les valeurs avec un nombre aleatoire entre 0 et 99

        Random random = new Random();
        val1 = random.nextInt(100);
        val2 = random.nextInt(100);
        //differente interpretations en fonctions du choix d'exercice

        switch (choixOperateur)
        {
            case "+":
                resultat = val1 + val2;
                break;
            case "-":
                //afin de ne pas avoir un nombre negatif

                while(val1<val2)
                {
                    random = new Random();
                    val1 = random.nextInt(100);
                    val2 = random.nextInt(100);
                }
                resultat = val1 - val2;
                break;
            case "/":
                resultat = (double)val1 / (double)val2;
                //afin d'obtenir uniquement 1 chiffre après la virgule

                String res = String.valueOf(resultat);
                int index = res.indexOf('.');
                resultat = Double.valueOf(res.substring(0, index+2));
                break;
            case "*":
                resultat = val1 * val2;
                break;
        }
        //affichage ecran

        calcul.setText(val1+choixOperateur+val2+" = ");
    }

    public void precedentBTN(View view) {
        precedentBTN.setVisibility(View.INVISIBLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(PageExercicesMaths.this);
        builder.setTitle("Attention !");
        builder.setMessage("Si vous quittez l'exercice toute progression sera perdue");
        builder.setPositiveButton("D'accord, quitter maintenant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compteur.setText("");
                finish();
                Intent intentPageMeu = new Intent(PageExercicesMaths.this,PageMenu.class);
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

