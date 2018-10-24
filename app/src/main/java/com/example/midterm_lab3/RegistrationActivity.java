package com.example.midterm_lab3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String USERS = "users";

    private EditText mEmailInput;
    private EditText mPasswordInput;
    private EditText mNameInput;
    private EditText mAgeInput;

    private Button mSignUpButton;

    private TextView mLoginButton;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference(USERS);

        initUI();
    }

    private void initUI() {
        mEmailInput = findViewById(R.id.registration_email);
        mPasswordInput = findViewById(R.id.registration_password);
        mNameInput = findViewById(R.id.registration_name);
        mAgeInput = findViewById(R.id.registration_age);

        mLoginButton = findViewById(R.id.registration_login);
        mSignUpButton = findViewById(R.id.registration_registerBtn);

        mLoginButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

        mUser = mFirebaseAuth.getCurrentUser();

        if (mUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        Intent LoginActivity = new Intent(this, LoginActivity.class);
        switch (v.getId()) {
            case (R.id.registration_login):
                startActivity(LoginActivity);
                finish();
                break;
            case (R.id.registration_registerBtn):
                validateAndSignUp(mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString());

        }
    }
    private void validateAndSignUp(String email, String password) {
        if (isValid(email, password)) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uId = task.getResult().getUser().getUid();

                                saveUserData(uId);

                                Toast.makeText(RegistrationActivity.this, R.string.success_sign_up_message,
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

                                return;

                            }

                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void saveUserData(String uId) {
        String name = mNameInput.getText().toString();
        String age = mAgeInput.getText().toString();

        if (name.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, R.string.data_validation_error, Toast.LENGTH_LONG).show();

            return;
        }

        User user = new User(uId, name, Integer.parseInt(age));

        mReference.child(uId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, R.string.success_save_message,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                    return;
                }

                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_validation_error, Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }
}
