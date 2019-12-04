package com.assessmentproj.evemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assessmentproj.evemanager.Models.EventModel;
import com.assessmentproj.evemanager.data.EventLab;
import com.assessmentproj.evemanager.data.MyValidators;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class AddEventActivity extends MainHostingActivity {


    //Views
    EditText mETEventName,mETEventDate,mETEventLocation,mETEventDescription;
    Button mBRegisterEvent;
    ProgressBar mProgressBar;
    TextView mTVError;

    //Data
    EventLab mEventLab; //manage events in database
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mEventLab=new EventLab(AddEventActivity.this);
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        getViewReference();

        addViewListeners();
    }

    private void getViewReference()
    {
        mETEventName=findViewById(R.id.ET_a_add_event_name);

        mETEventLocation=findViewById(R.id.ET_a_add_event_location);
        mETEventDescription=findViewById(R.id.ET_a_add_event_description);
        mBRegisterEvent=findViewById(R.id.B_a_add_event_register);
        mProgressBar=findViewById(R.id.PB_a_add_event_loading);
        mTVError=findViewById(R.id.TV_a_add_event_error);
    }

    private void addViewListeners()
    {
        mBRegisterEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                mTVError.setVisibility(View.GONE);
                if(!MyValidators.isNameValid(mETEventName.getText().toString()))
                {
                    mTVError.setVisibility(View.VISIBLE);
                    mTVError.setText("Event name should be alphanumeric and greater than 2 characters");
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(!MyValidators.isAddressValid(mETEventLocation.getText().toString()))
                {
                    mTVError.setVisibility(View.VISIBLE);
                    mTVError.setText("Event location should be alphanumeric and between 2 and 100 characters");
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(!MyValidators.isDEscriptionValid(mETEventDescription.getText().toString()))
                {
                    mTVError.setVisibility(View.VISIBLE);
                    mTVError.setText("Event description should be alphanumeric and between 0 and 500 characters");
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                //Add other validators

                EventModel eventModel=new EventModel();
                eventModel.setmEventName(mETEventName.getText().toString());
                eventModel.setmEventLocation(mETEventLocation.getText().toString());
                eventModel.setmEventDescription(mETEventDescription.getText().toString());
                eventModel.setmEventCreatedBy(mFirebaseUser.getUid());

                if(mEventLab.addNewEvent(eventModel))
                {
                    showToast("Event Registration Successful");
                    finish(); //End current activity
                }
                else
                {
                    showToast("Failed to register event");
                }



            }
        });
    }

    public void showToast(String message)
    {
        Toast.makeText(AddEventActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
