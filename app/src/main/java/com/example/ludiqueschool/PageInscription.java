package com.example.ludiqueschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class PageInscription extends AppCompatActivity
{
    private com.google.android.material.textfield.TextInputLayout mail,mdp,pseudo;
    private ArrayList<String> pseudoList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        pseudo =  findViewById(R.id.pseudoInscription);
        mail =  findViewById(R.id.emailInscription);
        mdp = findViewById(R.id.mdpInscription);

        pseudoList = new ArrayList<String>();
        db.collection("profil")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData().get("pseudo"));
                                pseudoList.add(document.getData().get("pseudo").toString());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void validerBTN(View view)
    {
        String alias = pseudo.getEditText().getText().toString();
        String email = mail.getEditText().getText().toString();
        String password = mdp.getEditText().getText().toString();

        if(email.isEmpty())
        {
            mail.setError(getText(R.string.erreurMail));
            mail.requestFocus();
            return;
        }

        if(alias.isEmpty())
        {
            pseudo.setError(getText(R.string.erreurPseudo));
            pseudo.requestFocus();
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

        if(alias.length()<2)
        {
            pseudo.setError(getText(R.string.erreurPseudo3));
            pseudo.requestFocus();
            return;
        }

        if(pseudoList.contains(alias))
        {
            pseudo.setError(getText(R.string.erreurPseudo2));
            pseudo.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d( "TAG","createUserWithEmail:success");
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI( user );
                    // Create a new user with a first and last name
                    Map<String, Object> user = new HashMap<>();
                    user.put("pseudo", alias);
                    user.put("email", email);

                    // Add a new document with a generated ID
                    db.collection("profil")
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

                    Toast.makeText(PageInscription.this, getText(R.string.authentificationreussi), Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intentMainActivity = new Intent(PageInscription.this,PageConnexion.class);
                    startActivity(intentMainActivity);
                } else {
                    Log.w("TAG","createUserWithEmail:failed");
                    Toast.makeText(PageInscription.this, getText(R.string.authentifiicationerreur), Toast.LENGTH_SHORT).show();
                    //updateUI( null );
                }
            }
        });
    }
}