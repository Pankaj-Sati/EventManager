package com.assessmentproj.evemanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.assessmentproj.evemanager.MainHostingActivity;
import com.assessmentproj.evemanager.Models.UserModel;
import com.assessmentproj.evemanager.R;
import com.assessmentproj.evemanager.data.MyValidators;
import com.assessmentproj.evemanager.data.UserLab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends MainHostingActivity
{
    //Views
    EditText mETEmail,mETPassword,mETName,mETAddress;
    TextView mTVError;
    Button mBRegister;
    ProgressBar mProgressBar;

    //Data
    FirebaseAuth mFirebaseAuth;
    UserLab mUserLab; //For managing user in firebase
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth=FirebaseAuth.getInstance(); //initializing authentication
        mUserLab=new UserLab(RegistrationActivity.this);
        getLayoutReference();

        addViewListeners();


    }

    private void addViewListeners()
    {

        mBRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                mTVError.setVisibility(View.GONE);
                if(! MyValidators.isEmailValid(mETEmail.getText().toString()))
                {
                    //Invalid email
                    mTVError.setText(R.string.invalid_username);
                    mTVError.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    return;

                }

                if(! MyValidators.isPasswordValid(mETPassword.getText().toString()))
                {
                    mTVError.setText(R.string.invalid_password);
                    mTVError.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }

                if(! MyValidators.isNameValid(mETName.getText().toString()))
                {
                    mTVError.setText(R.string.invalid_name);
                    mTVError.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(! MyValidators.isAddressValid(mETAddress.getText().toString()))
                {
                    mTVError.setText(R.string.invalid_address);
                    mTVError.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }

                registerUser();
            }
        });
    }

    private void registerUser()
    {
        mFirebaseAuth.createUserWithEmailAndPassword(mETEmail.getText().toString(),mETPassword.getText().toString())
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        mProgressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            //User registered successfully
                            UserModel userModel=new UserModel();
                            userModel.setmEmail(mETEmail.getText().toString());
                            userModel.setmUserID(task.getResult().getUser().getUid());
                            userModel.setmUserName(mETName.getText().toString());
                            userModel.setmUserAddress(mETAddress.getText().toString());

                            mUserLab.addNewUser(userModel); //Add user to database
                            Toast.makeText(RegistrationActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            startloginActivity();
                        }
                        else
                        {
                            //Registration Failed
                            Toast.makeText(RegistrationActivity.this,"Registration Failed! Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startloginActivity()
    {

        finish(); //Destroy the current activity so that user cannot come back to this
    }

    private void getLayoutReference()
    {
        mETEmail=findViewById(R.id.ET_a_register_email);
        mETPassword=findViewById(R.id.ET_a_register_password);
        mETName=findViewById(R.id.ET_a_register_name);
        mETAddress=findViewById(R.id.ET_a_register_address);
        mBRegister=findViewById(R.id.B_a_register_signup);
        mProgressBar=findViewById(R.id.PB_a_register_loading);
        mTVError=findViewById(R.id.TV_a_register_error);
    }
}
