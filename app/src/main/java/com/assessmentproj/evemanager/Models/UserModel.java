package com.assessmentproj.evemanager.Models;

import java.util.UUID;

/*
 * User model class that holds instance of a single user
 */
public class UserModel
{
    String mEmail;
    String mUserID;
    String mUserName;
    String mUserAddress;


    public String getmUserAddress() {
        return mUserAddress;
    }

    public void setmUserAddress(String mUserAddress) {
        this.mUserAddress = mUserAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

}
