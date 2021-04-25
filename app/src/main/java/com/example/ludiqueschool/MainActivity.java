package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // intent pour lancer la page inscription lorsque l'utilisateur clique sur le bouton inscription
    public void inscriptionBTN(View view){
        finish();
        Intent intentpageinscription = new Intent(this,PageInscription.class);
        startActivity(intentpageinscription);
    }
    // intent pour lancer la page connexion lorsque l'utilisateur clique sur le bouton connexion
    public void connexionBTN(View view){
        finish();
        Intent intentpageconnexion = new Intent(this, PageConnexion.class);
        startActivityForResult(intentpageconnexion,0);
    }

    // affiche une boite de dialogue de confirmation lorsque l'utilisateur clique sur la page quitter
    public void quitterBTN(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Quitter");
        builder.setMessage("Voulez-vous vraiment partir ?");
        builder.setPositiveButton("Oui, partir maintenant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}