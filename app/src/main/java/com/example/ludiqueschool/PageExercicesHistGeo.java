package com.example.ludiqueschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class PageExercicesHistGeo extends AppCompatActivity {
    private TextView titre;
    public static final String CATEGORIE_KEY= "categorie_key";//categorie du QCM (Histoire, Géo)
    private TextView compteur, enonceView,erreur;
    private RadioButton rep1Radio,rep2Radio,rep3Radio;
    private String choixCategorie,bd; //bd = string de la base de donnée en fonction du choix d'exo (geo ou hist)
    private int index,point,nbQuestions, nbQuestionsBD,rand;
    private Button suivantBTN, precedentBTN;
    private RadioGroup group;
    private ArrayList<String> enonceList,rep1List,rep2List,rep3List;  // liste des enonces et de toutes les reponse (ex : enonce(0) -> rep1list(0) , rep2list(0), rep3list(0))
    private ArrayList<Integer> indiceQuest; //On stock les questions deja posé pour pas qu'il y est redondance




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_exercices_hist_geo);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        titre = findViewById(R.id.titre);
        choixCategorie = getIntent().getStringExtra(CATEGORIE_KEY);

        Log.d("choix",choixCategorie);
        //choix categorie pour la BD
        if(choixCategorie.equals(PageMenu.HISTOIRE))
        {
            titre.setText(getText(R.string.histoireexo));
            bd = "histoire";
        } else if (choixCategorie.equals(PageMenu.GEOGRAPHIE))
        {
            titre.setText(getText(R.string.geoexo));
            bd = "geographie";
        }
        //initialisation
        enonceList = new ArrayList<String>();
        rep1List = new ArrayList<String>();
        rep2List = new ArrayList<String>();
        rep3List = new ArrayList<String>();
        indiceQuest = new ArrayList<Integer>();
        rep1Radio = findViewById(R.id.rep1);
        rep2Radio = findViewById(R.id.rep2);
        rep3Radio = findViewById(R.id.rep3);
        group = findViewById(R.id.radioGroup);
        compteur = findViewById(R.id.compteur);
        enonceView = findViewById(R.id.enonce);
        index = 0;
        point =0;
        nbQuestionsBD = 0;
        nbQuestions = 10;
        suivantBTN = findViewById(R.id.suivantBTN);
        precedentBTN = findViewById(R.id.precedentBTN);
        compteur = (TextView) findViewById(R.id.compteur);
        erreur = (TextView) findViewById(R.id.erreur);
        //obtenir tous les enonces + questions dans la BD en fonctions de l'exo choisit (geo ou hist)
        db.collection(bd)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                enonceList.add(document.getData().get("enonce").toString());
                                rep1List.add(document.getData().get("rep1").toString());
                                rep2List.add(document.getData().get("rep2").toString());
                                rep3List.add(document.getData().get("rep3").toString());
                                nbQuestionsBD++;
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    //ce bouton gere 2 options (compris et suivant)
    public void lancerBTN(View view)
    {
        //page d'explication d'exercice + actualisation de la page ou fermeture
        if(suivantBTN.getText().equals("understand"))
        {
            enonceView.setText("");
            group.setVisibility(View.VISIBLE);
            precedentBTN.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {
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
            });
            //precedentBTN.setVisibility(View.VISIBLE);
            actualiserPage();
        }
        //question suivante
        else if(suivantBTN.getText().equals("next"))
        {

            //si on arrive a la derniere question on ouvre la pageResultat en faisant passer une liste
            //contenant les points (note) + le choix d'exercice (geo ou hist)
            if(index+1 == 10)
            {
                Intent intentPageResultat = new Intent(this, PageResultat.class);
                //intentPageResultat.putExtra(PageResultat.BONNE_REPONSE, String.valueOf(point));
                ArrayList<String> list = new ArrayList<>();
                list.add(String.valueOf(point));
                list.add(getIntent().getStringExtra(CATEGORIE_KEY));
                intentPageResultat.putStringArrayListExtra(PageResultat.BONNE_REPONSE,list);
                startActivity(intentPageResultat);
                finish();
            }
            //sinon prochaine question

            else
            {
                erreur.setText("");
                index++;
                actualiserPage();
            }
        }

    }
    //afficher si la reponse est bonne ou pas quand un radiobutton(une reponse) est selectionné
    public void radioClick(View view)
    {
        String choixRep = ((RadioButton) view).getText().toString();
        if(choixRep.equals(rep2List.get(rand)))
        {
            erreur.setText("Bonne réponse !!! ");
            point++;
            erreur.setTextColor(GREEN);
        }
        else
        {
            erreur.setText("Faux, la bonne réponse était : "+rep2List.get(rand));
            erreur.setTextColor(RED);
        }
        suivantBTN.setEnabled(true);
        ((RadioButton) view).setChecked(false);
        rep1Radio.setEnabled(false);
        rep2Radio.setEnabled(false);
        rep3Radio.setEnabled(false);
    }

    private void actualiserPage()
    {
        compteur.setText((index+1)+"/"+nbQuestions);
        //tant que le joueur n'a pas choisit une reponse, il ne peut pas passer a la question suivante
        suivantBTN.setEnabled(false);
        //choisir une question aleatoirement et sans redondance

        Random random = new Random();
        rand = random.nextInt(nbQuestionsBD);
        while(indiceQuest.contains(rand))
        {
            rand = random.nextInt(nbQuestionsBD);
        }
        indiceQuest.add(rand);

        enonceView.setText(enonceList.get(rand));
        int secondPoteauxHasard  = random.nextInt(6);
        //affichage des questions aleatoirement (car la reponse est toujours dans la rep2list)

        switch (secondPoteauxHasard)
        {
            case 0:
                rep1Radio.setText(rep1List.get(rand));
                rep2Radio.setText(rep2List.get(rand));
                rep3Radio.setText(rep3List.get(rand));
                break;
            case 1:
                rep1Radio.setText(rep1List.get(rand));
                rep2Radio.setText(rep3List.get(rand));
                rep3Radio.setText(rep2List.get(rand));
                break;
            case 2:
                rep1Radio.setText(rep2List.get(rand));
                rep2Radio.setText(rep1List.get(rand));
                rep3Radio.setText(rep3List.get(rand));
                break;
            case 3:
                rep1Radio.setText(rep2List.get(rand));
                rep2Radio.setText(rep3List.get(rand));
                rep3Radio.setText(rep1List.get(rand));
                break;
            case 4:
                rep1Radio.setText(rep2List.get(rand));
                rep2Radio.setText(rep1List.get(rand));
                rep3Radio.setText(rep3List.get(rand));
                break;
            case 5:
                rep1Radio.setText(rep3List.get(rand));
                rep2Radio.setText(rep2List.get(rand));
                rep3Radio.setText(rep1List.get(rand));
                break;
        }

        suivantBTN.setText("next");
        suivantBTN.setEnabled(false);
        rep1Radio.setEnabled(true);
        rep2Radio.setEnabled(true);
        rep3Radio.setEnabled(true);
    }

    public void menuBTN(View view)
    {
        finish();
        Intent intentPageMeu = new Intent(PageExercicesHistGeo.this,PageMenu.class);
        startActivity(intentPageMeu);
    }
}
