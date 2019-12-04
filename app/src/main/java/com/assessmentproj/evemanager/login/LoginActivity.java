package com.assessmentproj.evemanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.assessmentproj.evemanager.AddEventActivity;
import com.assessmentproj.evemanager.HomeActivity;
import com.assessmentproj.evemanager.MainHostingActivity;
import com.assessmentproj.evemanager.R;
import com.assessmentproj.evemanager.data.MyValidators;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends MainHostingActivity
{
    //Views
    EditText mETEmail,mETPassword;
    Button mBSignIn,mBRegister;
    ProgressBar mProgressBar;
    TextView mTVError;

    //Data
   FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth=FirebaseAuth.getInstance();

        getLayoutReference();

        addViewListeners();

    }

    private void addViewListeners()
    {
        mBSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                mTVError.setVisibility(View.GONE);
                if(! MyValidators.isEmailValid(mETEmail.getText().toString()))
                {
                    mTVError.setVisibility(View.VISIBLE);
                    mTVError.setText("Invalid email address");
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(! MyValidators.isPasswordValid(mETPassword.getText().toString()))
                {
                    mTVError.setVisibility(View.VISIBLE);
                    mTVError.setText("Password should have 5 characters and should not contain white space");
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                performLogin();
            }
        });

        mBRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent); //Launch signup activity
            }
        });
    }

    private void performLogin()
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mTVError.setVisibility(View.GONE);
        mFirebaseAuth.signInWithEmailAndPassword(mETEmail.getText().toString(),mETPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            //Logged-in
                            showToast("Login Successful");
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish(); //Finish current activity
                        }
                        else
                        {
                            showToast("Login Failed! Invalid email or password");
                            mTVError.setVisibility(View.VISIBLE);
                            mTVError.setText("Login Failed! Invalid email or password");
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void getLayoutReference()
    {
        mETEmail=findViewById(R.id.ET_a_login_username);
        mETPassword=findViewById(R.id.ET_a_login_password);
        mBSignIn=findViewById(R.id.B_a_login_login);
        mBRegister=findViewById(R.id.B_a_login_signup);
        mProgressBar=findViewById(R.id.PB_a_login_loading);
        mTVError=findViewById(R.id.TV_a_login_error);
    }

    public void showToast(String message)
    {
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
