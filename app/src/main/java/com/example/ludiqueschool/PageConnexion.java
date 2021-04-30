package com.example.ludiqueschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageConnexion extends AppCompatActivity
{
    private com.google.android.material.textfield.TextInputLayout mail,mdp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String pseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mail =  findViewById(R.id.textFieldlogin);
        mdp = findViewById(R.id.textFieldpasseword);
    }


    public void validerBTN(View view)
    {
        String email = mail.getEditText().getText().toString();
        String password = mdp.getEditText().getText().toString();

        if(email.isEmpty())
        {
            mail.setError(getText(R.string.erreurMail));
            mail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            mdp.setError(getText(R.string.erreurMdp));
            mdp.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            mdp.setError(getText(R.string.MDPcourt));
            mdp.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mail.setError(getText(R.string.erreurMail2));
            mail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d( "TAG","signUserWithEmail:success");
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI( user );

                    db.collection("profil")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG", document.getId() + " => " + document.getData());
                                            if(document.getData().containsValue(email))
                                            {
                                                pseudo = document.getData().get("pseudo").toString();
                                                Log.d("OKKK", pseudo);
                                                SharedPreferences sharedPref = getSharedPreferences("sharePref", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.putString("pseudo", pseudo);
                                                editor.putString("mail", email);
                                                editor.apply();

                                                Toast.makeText(PageConnexion.this, getText(R.string.authentificationreussi), Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent intentMainActivity = new Intent(PageConnexion.this,PageMenu.class);
                                                startActivity(intentMainActivity);
                                            }
                                        }
                                    } else {
                                        Log.w("TAG", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                } else {
                    Log.w("TAG","signUserWithEmail:failed");
                    Toast.makeText(PageConnexion.this, getText(R.string.authentifiicationerreur), Toast.LENGTH_SHORT).show();
                    //updateUI( null );
                }
            }
        });



    }
}