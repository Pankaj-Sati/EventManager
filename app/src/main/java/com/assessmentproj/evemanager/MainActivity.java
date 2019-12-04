package com.assessmentproj.evemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.assessmentproj.evemanager.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/* This is the launcher activity that checks whether a user is logged-in or not and sends the use to appropriate page

 */
public class MainActivity extends AppCompatActivity
{
    FirebaseAuth mFirebaseAuth; //For authenticating firebase user
    FirebaseUser mFirebaseUser; //For holding instance of currently logged-in user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth=FirebaseAuth.getInstance(); //Initializing firebase authentication
        mFirebaseUser=mFirebaseAuth.getCurrentUser();//Get current logged in user
        if(mFirebaseUser==null)
        {
            //No user is logged in
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); //Destroy the current activity so that user cannot come back to this
        }
        else
        {
            //User is logged-in
            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); //Destroy the current activity so that user cannot come back to this
        }
    }
}
