package com.assessmentproj.evemanager.data;

import android.util.Patterns;

import java.util.regex.Pattern;

/*
    This class holds all global validators
 */
public class MyValidators
{
    public static Pattern NAME_PATTERN=Pattern.compile("^[A-Za-z]{2,50}$");
    public static Pattern ADDRESS_PATTERN=Pattern.compile("^[A-Za-z0-9]{2,100}$");
    public static Pattern DESCRIPTION_PATTERN=Pattern.compile("^[A-Za-z0-9]{0,500}$");
    public static Pattern PASSWORD_PATTERN=Pattern.compile("^[\\S]{0,500}$");
    public static boolean isEmailValid(String email)
    {
        if(email==null || email.length()<2)
        {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isNameValid(String name)
    {
        if(name==null)
        {
            return false;
        }

        return NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isAddressValid(String address)
    {
        if(address==null)
        {
            return false;
        }

        return ADDRESS_PATTERN.matcher(address).matches();
    }
    public static boolean isDEscriptionValid(String desc)
    {
        if(desc==null)
        {
            return false;
        }

        return DESCRIPTION_PATTERN.matcher(desc).matches();
    }
    public static boolean isPasswordValid(String pass)
    {
        if(pass==null)
        {
            return false;
        }

        return PASSWORD_PATTERN.matcher(pass).matches();
    }
}
