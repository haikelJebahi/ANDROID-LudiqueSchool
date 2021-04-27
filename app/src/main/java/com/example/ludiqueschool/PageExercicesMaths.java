package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Random;

public class PageExercicesMaths extends AppCompatActivity {
    public static final String DIFFICULTE_KEY= "difficulte_key";//choix du niveau
    private Button suivantBTN, precedentBTN;
    private int index, nbQuestions,val1,val2,resultat;
    private TextView calcul, compteur,erreur;
    private String choixOperateur;
    private EditText reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages_exercices_maths);

        index = 0;
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

        public void suivantBTN(View view) {
            if(suivantBTN.getText().equals("confirm"))
            {
                if(reponse.getText().equals(""))
                {
                    erreur.setText("Réponse vide !!! ");
                }
                else
                {
                    if(reponse.getText().equals(String.valueOf(resultat)))
                    {
                        erreur.setText("Bonne réponse !!! ");
                        suivantBTN.setText("Next");
                    }
                    else
                    {
                        String faux = "Faux, la bonne réponse est "+String.valueOf(resultat);
                        erreur.setText(faux);
                        suivantBTN.setText("Next");
                    }
                }
            }
            else {
                erreur.setText("");
                index++;
                suivantBTN.setText("Confirm");
                actualiserPage();
            }
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

    private void actualiserPage()
    {
            compteur.setText((index+1)+"/"+nbQuestions);
            Random random = new Random();
            val1 = random.nextInt(100);
            val2 = random.nextInt(100);
            resultat = val1 + val2;

           calcul.setText(val1+choixOperateur+val2+" = ");

            //Si on est à l'index 10/10
            if (index==nbQuestions-1) {

            } else {

            }
        }
}
