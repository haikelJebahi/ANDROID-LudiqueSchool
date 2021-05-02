package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PageMenu extends AppCompatActivity {

    public static final String HISTOIRE = "Histoire";
    public static final String GEOGRAPHIE = "Géographie";
    public TextView pseudo;
    private String alias;
    private Button stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_menu);

        pseudo =  findViewById(R.id.pseudoMenu);

        SharedPreferences sharedPref = getSharedPreferences("sharePref",Context.MODE_PRIVATE);
        alias = sharedPref.getString("pseudo", "default");
        if(alias.equals("V") || alias.equals("default") )
        {
            pseudo.setText(pseudo.getText().toString()+ " " + getText(R.string.visiteur));
        }
        else
        {
            stat =  findViewById(R.id.stat);
            stat.setVisibility(View.VISIBLE);
            pseudo.setText(pseudo.getText().toString()+ " " + alias);
        }

    }

    public void mathBTN(View view) {
        Intent intentPageChoixDifficulte = new Intent(this, PageChoixDifficulte.class);
       // intentPageChoixDifficulte.putExtra(PageChoixDifficulte.TYPE_KEY,"Mathématiques");
        startActivity(intentPageChoixDifficulte);
    }

    public void histoireBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
        intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY,getHISTOIRE());
        startActivity(intentPageExercicesHistGeo);
    }

    public void geographieBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
        intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY,getGEOGRAPHIE());
        startActivity(intentPageExercicesHistGeo);
    }

    public void statistiqueBTN(View view) {
        Intent intentPageStat = new Intent(this, PageStatistique.class);
        startActivity(intentPageStat);
    }

    public void deconnexionBTN(View view) {
        //Log.d("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Log.d( "OKKKKK",alias);
        if(alias.equals("V") || alias.equals("default") )
        {
            Log.d( "OKKKKK","AHHHHAHHAHA");
            Toast.makeText(PageMenu.this, "Sign out : success", Toast.LENGTH_SHORT).show();
            finish();
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
        else
        {
            FirebaseAuth.getInstance().signOut();

            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Log.d( "TAG","signOutUserWithEmail:success");
                Toast.makeText(PageMenu.this, "Sign out : success", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getSharedPreferences("sharePref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("pseudo", "V");
                editor.putString("mail", "V");
                editor.apply();
                finish();
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
            } else {
                Log.w("TAG","signOutUserWithEmail:failed");
                Toast.makeText(PageMenu.this, "Sign out : failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getGEOGRAPHIE() { return GEOGRAPHIE;}
    public String getHISTOIRE(){ return HISTOIRE;}
}