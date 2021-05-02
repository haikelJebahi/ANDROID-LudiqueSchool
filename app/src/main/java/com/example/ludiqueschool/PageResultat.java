package com.example.ludiqueschool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageResultat extends AppCompatActivity
{
    public static final String BONNE_REPONSE ="bonne reponse";
    private TextView note,commentaire;
    private String point,choix,pseudo,path; //path = chemin d'un document qui comporte les données d'un seul joueur pour un seul exo
    private int nbP;
    ArrayList<String> list; //liste envoyé par les pages d'exercices avec les points et l'exo (+,*,/,-,geo,hist)
    private FirebaseFirestore db; //BD des infos stockées dans la BD (questions, profil, stats, etc...)
    private boolean exist; //savoir si le joueur a deja fait cette exercice
    private int pt,coeff; //pt(note) et nombre de note dans la BD
    private double moyenne; //moyenne de toutes les notes deja existantes
    private Button stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_resultat);

        commentaire = (TextView) findViewById(R.id.commentaire);
        note = (TextView) findViewById(R.id.note);
        stat = findViewById(R.id.stat);
        //recuperation des données envoyés par les pages d'exercices

        list=getIntent().getExtras().getStringArrayList(BONNE_REPONSE);
        point =  list.get(0);
        choix = list.get(1);

        note.setText(point+"/10");
        //note que nous mettrons dans la base de donnant en tant que LASTNOTE
        nbP = Integer.valueOf(point);
        //commentaire en fonction de la note
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
        //sauvegarde des notes en fonctions des exercices

        if(choix.equals(PageMenu.HISTOIRE) )
        {
            saveNote("bdhist");
        }
        else if(choix.equals(PageMenu.GEOGRAPHIE))
        {
            saveNote("bdgeo");
        }
        else
        {
            switch (choix)
            {
                case "+":
                    saveNote("bdmathadd");
                    break;
                case "/":
                    saveNote("bdmathdiv");
                    break;
                case "-":
                    saveNote("bdmathsous");
                    break;
                case "*":
                    saveNote("bdmathmul");
                    break;
            }
        }

    }

    public void retourBTN(View view) {
        finish();
        Intent intentPageMenu = new Intent(this, PageMenu.class);
        startActivity(intentPageMenu);
    }

    public void recommencerBTN(View view) {
        finish();
        if(choix.equals(PageMenu.HISTOIRE) || choix.equals(PageMenu.GEOGRAPHIE))
        {
            Intent intentPageHistGeo = new Intent(this, PageExercicesHistGeo.class);
            intentPageHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY, choix);
            startActivity(intentPageHistGeo);
        }
        else {
            Intent intentPageExercicesMaths = new Intent(this, PageExercicesMaths.class);
            intentPageExercicesMaths.putExtra(PageExercicesMaths.DIFFICULTE_KEY, choix);
            startActivity(intentPageExercicesMaths);
        }
    }

    public void statistiqueBTN(View view) {
        Intent intentPageStat = new Intent(this, PageStatistique.class);
        startActivity(intentPageStat);
    }

    public void saveNote(String bd)
    {
        //recuperation du pseudo dans sharedpreference

        SharedPreferences sharedPref = getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        pseudo = sharedPref.getString("pseudo", "default");
        //si le joueur nest pas un guest donc a un compte enregistré

        if(!pseudo.equals("V") && !pseudo.equals("default"))
        {
            exist = false;
            stat.setVisibility(View.VISIBLE);

            db = FirebaseFirestore.getInstance();
            //voir si le joueur a deja des données enregistrés dans cette exo

            db.collection(bd)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("TAG", document.getId() + " => " + document.getData().get("pseudo").toString());
                                    if(document.getData().get("pseudo").toString().equals(pseudo))
                                    //des données existes donc le joueur a deja joué a cette exo

                                    {
                                        path = document.getId();
                                        exist = true;
                                        pt = Integer.valueOf(point);
                                        moyenne = Double.valueOf(document.getData().get("moyenne").toString());
                                        coeff = Integer.valueOf(document.getData().get("coeff").toString());
                                        //On calcule donc la moyenne en fonction du coeff(nb de note) et de l'ancienne moyenne et de la nouvelle note;


                                        moyenne = (moyenne * (double)coeff) + pt;
                                        //nouvelleMoyenne = ((moyenne * (double)nbNote) + derniere note)/ nbNote+1)

                                        moyenne = moyenne / (double)(coeff+1);
                                    }
                                }
                                //si les données n'existe pas (premiere utilisation de cette exo) on creer un document de cette exo pour ce joueur dans

                                if(!exist)
                                {
                                    Log.d("HISTOIRE","okkk");
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("pseudo", pseudo);
                                    user.put("coeff", "1"); // on a qu'une seule note vu quon vient de le creer

                                    user.put("last", point);
                                    user.put("moyenne", point); //last et moyenne sont egaux car on a qu'une seule note

                                    // Add a new document with a generated ID
                                    db.collection(bd)
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error adding document", e);
                                                }
                                            });
                                }
                                //sinon on mets a jours les données deja existantes

                                else
                                {
                                    Log.d("HISTOIRE","okkkkk");
                                    db.collection(bd).document(path)
                                            .update(
                                                    "coeff", String.valueOf(coeff+1),
                                                    "moyenne", String.valueOf(moyenne),
                                                    "last", point
                                            );
                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }

    }
}
