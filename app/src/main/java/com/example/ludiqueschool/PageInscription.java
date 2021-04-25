package com.example.ludiqueschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PageInscription extends AppCompatActivity
{
    private com.google.android.material.textfield.TextInputLayout mail,mdp;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        mAuth = FirebaseAuth.getInstance();

        mail =  findViewById(R.id.emailInscription);
        mdp = findViewById(R.id.mdpInscription);
    }


    public void validerBTN(View view)
    {
        String email = mail.getEditText().getText().toString();
        String password = mdp.getEditText().getText().toString();

        if(email.isEmpty())
        {
            mail.setError("Email vide");
            mail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            mdp.setError("Mot de passe vide");
            mdp.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            mdp.setError("Mot de passe trop court (<6)");
            mdp.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mail.setError("Email invalide");
            mail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d( "TAG", "createUserWithEmail:success" );
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI( user );
                    Toast.makeText(PageInscription.this, "Authentication réussi", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intentMainActivity = new Intent(PageInscription.this,PageConnexion.class);
                    startActivity(intentMainActivity);
                } else {
                    Log.w("TAG", "createUserWithEmail:failed");
                    Toast.makeText(PageInscription.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    //updateUI( null );
                }
            }
        });
    }
}