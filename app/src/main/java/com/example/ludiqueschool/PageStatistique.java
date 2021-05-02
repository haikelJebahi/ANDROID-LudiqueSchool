package com.example.ludiqueschool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PageStatistique extends AppCompatActivity
{
    String pseudo,nomBd;
    GridLayout grille;
    Button histMoy,histLast;
    Button geoMoy,geoLast;
    Button mathaddMoy,mathaddLast;
    Button mathsousMoy,mathsousLast;
    Button mathdivMoy,mathdivLast;
    Button mathmulMoy,mathmulLast;
    private FirebaseFirestore db; //BD des infos stockÃ©es dans la BD (questions, profil, stats, etc...)
    private ArrayList<String> liste;//liste des exos
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_statistique);
        //liste des exos

        liste = new ArrayList<String>();
        liste.add("bdgeo");
        liste.add("bdhist");
        liste.add("bdmathadd");
        liste.add("bdmathsous");
        liste.add("bdmathdiv");
        liste.add("bdmathmul");
        //on recupere le pseudo de l'utilisateur

        SharedPreferences sharedPref = getSharedPreferences("sharePref",Context.MODE_PRIVATE);
        pseudo = sharedPref.getString("pseudo", "default");
        db = FirebaseFirestore.getInstance();

        histMoy= findViewById(R.id.histMoy);
        histLast=findViewById(R.id.histLast);

        geoMoy= findViewById(R.id.geoMoy);
        geoLast=findViewById(R.id.geoLast);

        mathaddMoy= findViewById(R.id.matAddMoy);
        mathaddLast= findViewById(R.id.matAddLast);

        mathsousMoy= findViewById(R.id.matsousMoy);
        mathsousLast= findViewById(R.id.matsousLast);

        mathdivMoy= findViewById(R.id.matdivMoy);
        mathdivLast= findViewById(R.id.matdivLast);

        mathmulMoy= findViewById(R.id.matmulMoy);
        mathmulLast= findViewById(R.id.matmulLast);
        //ne marche pas donc nous avons fait sans la boucle 1 par 1 (donc code plus long malheuresement
        /*for(i=0;i<liste.size();i++)
        {
            nomBd = liste.get(i);
            Log.d("BD", nomBd);
            db.collection(nomBd)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("TAG", document.getId() + " => " + document.getData());
                                    if(document.getData().containsValue(pseudo))
                                    {
                                        Log.d("BDDDDD", nomBd);
                                        if(nomBd.equals("bdgeo"))
                                        {
                                            geoMoy.setText(document.getData().get("moyenne").toString());
                                            geoLast.setText(document.getData().get("last").toString());
                                        }
                                        else if(nomBd.equals("bdhist"))
                                        {
                                            histMoy.setText(document.getData().get("moyenne").toString());
                                            histLast.setText(document.getData().get("last").toString());
                                        }
                                        else if(nomBd.equals("bdmathadd"))
                                        {
                                            mathaddMoy.setText(document.getData().get("moyenne").toString());
                                            mathaddLast.setText(document.getData().get("last").toString());
                                        }
                                        else if(nomBd.equals("bdmathsous"))
                                        {
                                            mathsousMoy.setText(document.getData().get("moyenne").toString());
                                            mathsousLast.setText(document.getData().get("last").toString());
                                        }
                                        else if(nomBd.equals("bdmathdiv"))
                                        {
                                            mathdivMoy.setText(document.getData().get("moyenne").toString());
                                            mathdivLast.setText(document.getData().get("last").toString());
                                        }
                                        else if(nomBd.equals("bdmathmul"))
                                        {
                                            mathmulMoy.setText(document.getData().get("moyenne").toString());
                                            mathmulLast.setText(document.getData().get("last").toString());
                                        }
                                    }
                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }*/
        //AFFICHAGE DE TOUTES LES TABLES EN FONCTIONS DE L'EXO

        db.collection("bdgeo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                        geoMoy.setText(document.getData().get("moyenne").toString());
                                        geoLast.setText(document.getData().get("last").toString());
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                }}});

        db.collection("bdhist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                    histMoy.setText(document.getData().get("moyenne").toString());
                                    histLast.setText(document.getData().get("last").toString());
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        }}});

        db.collection("bdmathadd")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                    mathaddMoy.setText(document.getData().get("moyenne").toString());
                                    mathaddLast.setText(document.getData().get("last").toString());
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        }}});

        db.collection("bdmathdiv")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                    mathdivMoy.setText(document.getData().get("moyenne").toString());
                                    mathdivLast.setText(document.getData().get("last").toString());
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        }}});

        db.collection("bdmathsous")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                    mathsousMoy.setText(document.getData().get("moyenne").toString());
                                    mathsousLast.setText(document.getData().get("last").toString());
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        }}});

        db.collection("bdmathmul")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                if(document.getData().containsValue(pseudo))
                                {
                                    mathmulMoy.setText(document.getData().get("moyenne").toString());
                                    mathmulLast.setText(document.getData().get("last").toString());
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        }}});


    }

}
