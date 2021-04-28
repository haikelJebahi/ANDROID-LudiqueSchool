package com.example.ludiqueschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PageMenu extends AppCompatActivity {

    public static final String HISTOIRE = "Histoire";
    public static final String GEOGRAPHIE = "Géographie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_menu);
    }

    public void mathBTN(View view) {
        Intent intentPageChoixDifficulte = new Intent(this, PageChoixDifficulte.class);
       // intentPageChoixDifficulte.putExtra(PageChoixDifficulte.TYPE_KEY,"Mathématiques");
        startActivity(intentPageChoixDifficulte);
    }

    public void histoireBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
        intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY,getGEOGRAPHIE());
        startActivity(intentPageExercicesHistGeo);
    }

    public void geographieBTN(View view) {
        Intent intentPageExercicesHistGeo = new Intent(this, PageExercicesHistGeo.class);
        intentPageExercicesHistGeo.putExtra(PageExercicesHistGeo.CATEGORIE_KEY,getHISTOIRE());
        startActivity(intentPageExercicesHistGeo);
    }

    public void deconnexionBTN(View view) {
        Log.d("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FirebaseAuth.getInstance().signOut();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.d( "TAG","signOutUserWithEmail:success");
            Toast.makeText(PageMenu.this, "Sign out : success", Toast.LENGTH_SHORT).show();
            finish();
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        } else {
            Log.w("TAG","signOutUserWithEmail:failed");
            Toast.makeText(PageMenu.this, "Sign out : failed", Toast.LENGTH_SHORT).show();
        }
    }

    public String getGEOGRAPHIE() { return GEOGRAPHIE;}
    public String getHISTOIRE(){ return HISTOIRE;}
}