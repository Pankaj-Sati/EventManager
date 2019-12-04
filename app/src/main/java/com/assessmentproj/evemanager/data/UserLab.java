package com.assessmentproj.evemanager.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.assessmentproj.evemanager.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 For managing users in the firebase database
 */
public class UserLab
{
    Context mContext; //Context of calling component
    DatabaseReference mFirebaseDatabase;
    FirebaseUser mFirebaseUser;

    public UserLab(Context context)
    {
        mContext=context;
        mFirebaseDatabase=FirebaseDatabase.getInstance().getReference(); //Get reference of database
        mFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    }

    public boolean addNewUser(UserModel userModel) {
        //Return false for failure and true when successful
        final boolean[] isSuccessful = {false};
        if (userModel == null)
        {
            return isSuccessful[0];
        }
        String userID=userModel.getmUserID();
       mFirebaseDatabase.child(DatabaseConstants.UsersCollection.KEY).child(userID).child(DatabaseConstants.UsersCollection.UserElements.NAME).setValue(userModel.getmUserName());
        mFirebaseDatabase.child(DatabaseConstants.UsersCollection.KEY).child(userID).child(DatabaseConstants.UsersCollection.UserElements.EMAIL).setValue(userModel.getmEmail());
        mFirebaseDatabase.child(DatabaseConstants.UsersCollection.KEY).child(userID).child(DatabaseConstants.UsersCollection.UserElements.ADDRESS).setValue(userModel.getmUserAddress());
        mFirebaseDatabase.child(DatabaseConstants.UsersCollection.KEY).child(userID).child(DatabaseConstants.UsersCollection.UserElements.ID).setValue(userModel.getmUserID());
        isSuccessful[0]=true;

       /*mFirebaseDatabase.child(DatabaseConstants.UsersCollection.KEY).child(userID).setValue(userModel, new DatabaseReference.CompletionListener() {
           @Override
           public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

               Log.d("UserLab",databaseError.toString());
               if(databaseError==null)
               {
                   isSuccessful[0] =true;
               }
               else
               {
                   isSuccessful[0] =false;
               }
           }
       });
       */

        return isSuccessful[0];

    }
}
