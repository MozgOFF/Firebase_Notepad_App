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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailInput;
    private EditText mPasswordInput;

    private Button mLoginButton;

    private TextView mSignUpButton;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mFirebaseAuth = FirebaseAuth.getInstance();

        mUser = mFirebaseAuth.getCurrentUser();

        if (mUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        initUI();
    }

    private void initUI() {
        mEmailInput = findViewById(R.id.login_email);
        mPasswordInput = findViewById(R.id.login_password);

        mLoginButton = findViewById(R.id.login_loginBtn);
        mSignUpButton = findViewById(R.id.login_register);

        mLoginButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.login_loginBtn):
                validateAndSignIn(mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString());
                break;
            case (R.id.login_register):
                startActivity(new Intent(this, RegistrationActivity.class));
                finish();
                break;
        }
    }

    private void validateAndSignIn(String email, String password) {
        if (isValid(email, password)) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.success_sign_in_message,
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                return;
                            }

                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_validation_error, Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }
}
