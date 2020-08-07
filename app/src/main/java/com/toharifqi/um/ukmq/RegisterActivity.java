package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toharifqi.um.ukmq.model.UserModel;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout usernameInput, emailInput, passwordInput, retypePasswordInput;
    RadioGroup userRoleRadio;
    RadioButton radioButton;

    FirebaseAuth fAuth;
    DatabaseReference userRef;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.name_register);
        emailInput = findViewById(R.id.email_register);
        passwordInput = findViewById(R.id.password_register);
        retypePasswordInput = findViewById(R.id.retype_password_register);
        userRoleRadio = findViewById(R.id.radio_role);
        radioButton = findViewById(R.id.investor_button);

        userRef = FirebaseDatabase.getInstance().getReference();

        //to display loading dialog
        dialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).build();
        dialog.setMessage("Mohon tunggu...");

        fAuth = FirebaseAuth.getInstance();
    }

    //these method is for validate any input from users
    private Boolean validateUserRole(){
        if (userRoleRadio.getCheckedRadioButtonId() == -1){
            radioButton.setError("pilih salah satu user!");
            return false;
        }else {
            radioButton.setError(null);
            return true;
        }
    }
    private Boolean validateName() {
        String val = usernameInput.getEditText().getText().toString();

        if (val.isEmpty()) {
            usernameInput.setError("Nama lengkap tidak boleh kosong");
            return false;
        } else {
            usernameInput.setError(null);
            usernameInput.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = emailInput.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailInput.setError("Email tidak boleh kosong");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailInput.setError("Email tidak valid");
            return false;
        } else {
            emailInput.setError(null);
            emailInput.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passwordInput.getEditText().getText().toString();
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
            passwordInput.setError("Password tidak boleh kosong");
            return false;
        } else if (val.length()<=8) {
            passwordInput.setError("Password kurang panjang");
            return false;
        } else {
            passwordInput.setError(null);
            passwordInput.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateRetypePassword() {
        String val = passwordInput.getEditText().getText().toString();
        String val2 = retypePasswordInput.getEditText().getText().toString();

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
            retypePasswordInput.setError("Password tidak boleh kosong");
            return false;
        } else if(!val.equals(val2)){
            retypePasswordInput.setError("Password tidak sesusai");
            return false;
        } else if (val.length()<=8) {
            retypePasswordInput.setError("Password kurang panjang");
            return false;
        } else {
            retypePasswordInput.setError(null);
            retypePasswordInput.setErrorEnabled(false);
            return true;
        }
    }


    public void toLogin(View view){
        onBackPressed();
    }

    //register new user
    public void toRegister(View view){

        if(!validateUserRole() | !validateName() | !validatePassword() | !validateEmail() | !validateRetypePassword()){
            return;
        }

        String email = emailInput.getEditText().getText().toString().trim();
        String password = passwordInput.getEditText().getText().toString().trim();

        dialog.show();

        //register users to firebase
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Anda berhasil terdaftar!", Toast.LENGTH_LONG).show();
                    onAuthSuccess(task.getResult().getUser());

                }else {
                    Toast.makeText(RegisterActivity.this, "Maaf, terjadi kesalahan "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        int userType;
        String userName = usernameInput.getEditText().getText().toString();
        if (userRoleRadio.getCheckedRadioButtonId() == R.id.ukm_button){
            userType = 1;
        }else {
            userType = 2;
        }
        //write new user
        writeNewUser(user.getUid(), userType, userName, user.getEmail());
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void writeNewUser(String uid, int userType, String userName, String email) {
        UserModel user = new UserModel(userName, email, "", "", "", "", "", "", "", "", "", "", "", "", userType);
        userRef.child("users").child(uid).setValue(user);
    }

}
