package com.example.rideshareandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {


    private TextInputLayout mStatus;
    private Button mSavebtn;
    private ProgressDialog mProgress;
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mCurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentuser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        String status_value = getIntent().getStringExtra("status_value");
        mStatus = findViewById(R.id.status_input);
        mSavebtn = findViewById(R.id.status_save_btn);
        mStatus.getEditText().setText(status_value);
        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();
                final String status = mStatus.getEditText().getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            mProgress.dismiss();

                        }else{

                            Toast.makeText(StatusActivity.this, "There was some error in Saving Changes", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}