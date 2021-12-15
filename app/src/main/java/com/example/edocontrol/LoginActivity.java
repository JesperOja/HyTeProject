package com.example.edocontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    public static String EMAIL;
    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout root;
    Singleton user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignIn = findViewById(R.id.btnSingIn);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);
        user = Singleton.getInstance();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });
    }
    //User signing in
    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Login");
        dialog.setMessage("Insert login information");

        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_in_win = inflater.inflate(R.layout.authorization_window,null);
        dialog.setView(sign_in_win);

        final MaterialEditText email =  sign_in_win.findViewById(R.id.emailField);
        final MaterialEditText pass = sign_in_win.findViewById(R.id.passField);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(event.getAction()!=KeyEvent.ACTION_DOWN) {
                    return false;
                }
                if(event.getAction()==KeyEvent.KEYCODE_ENTER){

                    return true;
                }
                return false;
            }
        });

        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter email", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(pass.getText().toString().length() < 5){
                    Snackbar.make(root, "Password is to short", Snackbar.LENGTH_LONG).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        EMAIL = email.getText().toString();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        FirebaseDatabase.getInstance().getReference().push().setValue(email.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid());
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Authorization Error. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.show();

    }
    //User registration
    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Insert registration information");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_win = inflater.inflate(R.layout.register_window,null);
        dialog.setView(reg_win);

        final MaterialEditText email =  reg_win.findViewById(R.id.emailField);
        final MaterialEditText pass = reg_win.findViewById(R.id.passField);
        final MaterialEditText name = reg_win.findViewById(R.id.nameField);
        final MaterialEditText phone = reg_win.findViewById(R.id.phoneField);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void regInfo(DialogInterface dialogInterface, int which){
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Enter email", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Enter name", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(root, "Enter phone number", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(pass.getText().toString().length() < 5){
                    Snackbar.make(root, "Password is to short", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //User registration
                Snackbar.make(root, "User added!", Snackbar.LENGTH_SHORT).show();
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user.setUser(pass.getText().toString(),phone.getText().toString(),email.getText().toString(),name.getText().toString(),users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Registration Error. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                regInfo(dialogInterface, which);
            }
        });

        dialog.show();

    }

}