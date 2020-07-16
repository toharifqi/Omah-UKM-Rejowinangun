package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout header, headerBg;
    private TextInputLayout emailLogin, passwordLogin;

    AlertDialog dialog;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_login);

        //these are the views thar we are gonna animate bruh!
        header = findViewById(R.id.header_linearlayout);
        headerBg = findViewById(R.id.header_bg);
        emailLogin = findViewById(R.id.email_login);
        passwordLogin = findViewById(R.id.password_login);

        //to display loading dialog
        dialog = new SpotsDialog.Builder().setContext(LoginActivity.this).build();
        dialog.setMessage("Mohon tunggu...");

        fAuth = FirebaseAuth.getInstance();

        //if user already login once
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    //this is the method which i use to go to register activity and reanimate the views at the same time
    public void toRegister(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(header, "header_animate");
        pairs[1] = new Pair<View, String>(emailLogin, "email_animate");
        pairs[2] = new Pair<View, String>(passwordLogin, "password_animate");
        pairs[3] = new Pair<View, String>(headerBg, "header_bg_animate");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }

    //this method prevent the app to open the splash screen again, (kill the app instead of open the splash screen)
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
    
    //these method is to validate email and password
    private Boolean validateEmail() {
        String val = emailLogin.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailLogin.setError("Email tidak boleh kosong");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailLogin.setError("Email tidak valid");
            return false;
        } else {
            emailLogin.setError(null);
            emailLogin.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passwordLogin.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            passwordLogin.setError("Password tidak boleh kosong");
            return false;
        } else if (val.length()<=8) {
            passwordLogin.setError("Password kurang panjang");
            return false;
        } else {
            passwordLogin.setError(null);
            passwordLogin.setErrorEnabled(false);
            return true;
        }
    }

    public void toLogin (View view){
        if(!validatePassword() | !validateEmail()){
            return;
        }

        String email = emailLogin.getEditText().getText().toString().trim();
        String password = passwordLogin.getEditText().getText().toString().trim();

        dialog.show();
        //user authentication
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "tidak dapat login, pastikan email dan password anda benar!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
}
