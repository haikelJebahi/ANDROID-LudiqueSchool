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
    private String point,choix,pseudo,path;
    private int nbP;
    ArrayList<String> list;
    private FirebaseFirestore db;
    private boolean exist;
    private int pt,coeff;
    private double moyenne;
    private Button stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_resultat);

        commentaire = (TextView) findViewById(R.id.commentaire);
        note = (TextView) findViewById(R.id.note);
        stat = findViewById(R.id.stat);

        list=getIntent().getExtras().getStringArrayList(BONNE_REPONSE);
        point =  list.get(0);
        choix = list.get(1);

        note.setText(point+"/10");
        nbP = Integer.valueOf(point);

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
        SharedPreferences sharedPref = getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        pseudo = sharedPref.getString("pseudo", "default");
        if(!pseudo.equals("V") && !pseudo.equals("default"))
        {
            exist = false;
            stat.setVisibility(View.VISIBLE);

            db = FirebaseFirestore.getInstance();
            //voir si il existe data pour ce joueuer dans cette exo
            db.collection(bd)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("TAG", document.getId() + " => " + document.getData().get("pseudo").toString());
                                    if(document.getData().get("pseudo").toString().equals(pseudo))
                                    {
                                        path = document.getId();
                                        exist = true;
                                        pt = Integer.valueOf(point);
                                        moyenne = Double.valueOf(document.getData().get("moyenne").toString());
                                        coeff = Integer.valueOf(document.getData().get("coeff").toString());

                                        moyenne = (moyenne * (double)coeff) + pt;
                                        moyenne = moyenne / (double)(coeff+1);
                                    }
                                }
                                //si les données n'existe pas (premiere utilisation de cette exo)
                                if(!exist)
                                {
                                    Log.d("HISTOIRE","okkk");
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("pseudo", pseudo);
                                    user.put("coeff", "1");
                                    user.put("last", point);
                                    user.put("moyenne", point);

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
