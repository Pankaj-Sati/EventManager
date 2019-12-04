package com.assessmentproj.evemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainHostingActivity extends AppCompatActivity
{
    BroadcastReceiver mNetworkChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            Log.d("HostActivity","network Change Broadcast Received");
            if(checkIfOnline(context))
            {
                showToast(context,"Connection Established");
            }
            else
            {
                showToast(context,"You are currently Offline");
            }

        }
    };
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("HostActivity","On start");
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(mNetworkChangeReceiver,intentFilter,null,null);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.unregisterReceiver(mNetworkChangeReceiver);
    }

    private boolean checkIfOnline(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void showToast(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
