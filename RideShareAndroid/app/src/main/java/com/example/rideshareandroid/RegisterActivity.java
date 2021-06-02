package com.example.rideshareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegButton;
    private FirebaseAuth mAuth;
    private ProgressDialog regProgress;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDisplayName = (EditText)findViewById(R.id.regDisplay);
        mEmail = (EditText)findViewById(R.id.regEnmail);
        mPassword = (EditText)findViewById(R.id.regPassword);
        mRegButton = (Button) findViewById(R.id.regButton);
        regProgress = new ProgressDialog(this);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
        if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email)  || !TextUtils.isEmpty(password)  ){
            regProgress.setTitle("Registering User");
            regProgress.setMessage("Wait and watch");
            regProgress.setCanceledOnTouchOutside(false);
            regProgress.show();
            registerUser(display_name, email, password);

        }
            }
        });
    }

    private void registerUser(String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uId = current_user.getUid();
                            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", display_name);
                            userMap.put("status", "Hi I am using Ride SHare Application");
                            userMap.put("image", "messi");
                            userMap.put("thumbnail", "messi");
                            firebaseDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        regProgress.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });


                        } else {
                            regProgress.hide();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}