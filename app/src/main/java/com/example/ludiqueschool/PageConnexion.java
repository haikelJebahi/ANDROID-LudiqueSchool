package com.example.ludiqueschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PageConnexion extends AppCompatActivity
{
    private com.google.android.material.textfield.TextInputLayout mail,mdp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

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
                    Log.d( "TAG","createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI( user );
                    Toast.makeText(PageConnexion.this, getText(R.string.authentificationreussi), Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intentMainActivity = new Intent(PageConnexion.this,PageConnexion.class);
                    startActivity(intentMainActivity);
                } else {
                    Log.w("TAG","createUserWithEmail:failed");
                    Toast.makeText(PageConnexion.this, getText(R.string.authentifiicationerreur), Toast.LENGTH_SHORT).show();
                    //updateUI( null );
                }
            }
        });



    }
}