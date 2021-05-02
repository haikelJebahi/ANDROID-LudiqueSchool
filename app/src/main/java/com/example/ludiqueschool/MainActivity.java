package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On essaye d'obtenir le sharedpreference "pseudo" pour savoir si l'utilisateur
        //est deja connecté

        SharedPreferences sharedPref = getSharedPreferences("sharePref", Context.MODE_PRIVATE);

        pseudo = sharedPref.getString("pseudo", "default");

        Log.d("PSEUDO",pseudo);

        if(!pseudo.equals("V") && !pseudo.equals("default"))
        {
            finish();
            Intent intentMainActivity = new Intent(MainActivity.this,PageMenu.class);
            startActivity(intentMainActivity);
        }
    }

    // intent pour lancer la page inscription lorsque l'utilisateur clique sur le bouton inscription
    public void inscriptionBTN(View view){
        Intent intentpageinscription = new Intent(this,PageInscription.class);
        startActivity(intentpageinscription);
    }
    // intent pour lancer la page connexion lorsque l'utilisateur clique sur le bouton connexion
    public void connexionBTN(View view){

        Intent intentpageconnexion = new Intent(this, PageConnexion.class);
        startActivityForResult(intentpageconnexion,0);
    }

    // intent pour lancer la page connexion lorsque l'utilisateur clique sur le bouton connexion
    public void visiteurBTN(View view)
    {
        Intent intentMainActivity = new Intent(MainActivity.this,PageMenu.class);
        startActivity(intentMainActivity);
    }

    // affiche une boite de dialogue de confirmation lorsque l'utilisateur clique sur la page quitter
    public void quitterBTN(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getText(R.string.quitter));
        builder.setMessage(getText(R.string.partir));
        builder.setPositiveButton(getText(R.string.partirmaintenant), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
            }
        });
        builder.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}