package com.assessmentproj.evemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assessmentproj.evemanager.Models.EventModel;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Serializable;

public class EventDetailsActivity extends MainHostingActivity
{
    private static final String EVENT_DETAIL_KEY="event_details";
    private static final String EVENT_NAME_KEY ="event_name" ;
    private static final String EVENT_LOCATION_KEY ="event_location" ;
    private static final String EVENT_DESCRIPTION_KEY ="event_description" ;

    public static Intent createIntent(Context context, EventModel eventModel)
    {
        Intent intent=new Intent(context,EventDetailsActivity.class);

        intent.putExtra(EVENT_DETAIL_KEY,createEventBundle(eventModel));
        return intent;
    }

    private static Bundle createEventBundle(EventModel eventModel)
    {
        Bundle bundle=new Bundle();
        bundle.putString(EVENT_NAME_KEY,eventModel.getmEventName());
        bundle.putString(EVENT_LOCATION_KEY,eventModel.getmEventLocation());
        bundle.putString(EVENT_DESCRIPTION_KEY,eventModel.getmEventDescription());
        return bundle;
    }

    //View
    TextView mTVEventName,mTVEventLocation,mTVEventDescription;
    Button mBBack;

    //Data
    EventModel mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getEventFromIntent();
        getViewReference();

        addViewListeners();

        updateViews();
    }

    private void updateViews()
    {
        mTVEventName.setText(mEvent.getmEventName());
        mTVEventLocation.setText(mEvent.getmEventLocation());
        mTVEventDescription.setText(mEvent.getmEventDescription());
    }

    private EventModel getEventFromBundle(Bundle eventBundle)
    {
        if(eventBundle==null)
        {
            return null;
        }
        EventModel eventModel=new EventModel();
        eventModel.setmEventName(eventBundle.getString(EVENT_NAME_KEY));
        eventModel.setmEventLocation(eventBundle.getString(EVENT_LOCATION_KEY));
        eventModel.setmEventDescription(eventBundle.getString(EVENT_DESCRIPTION_KEY));
        return eventModel;
    }

    private void getEventFromIntent()
    {
        if(getIntent()!=null)
        {
            mEvent= getEventFromBundle(getIntent().getBundleExtra(EVENT_DETAIL_KEY));
            if(mEvent==null || mEvent.getmEventID()==null)
            {
                showToast("Invalid Event Details");
                finish();
            }
        }
        else
        {
            showToast("Invalid Event Details");
            finish();

        }
    }

    private void addViewListeners()
    {
        mBBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getViewReference()
    {
        mTVEventName=findViewById(R.id.TV_a_event_details_e_name);
        mTVEventLocation=findViewById(R.id.TV_a_event_details_e_location);
        mTVEventDescription=findViewById(R.id.TV_a_event_details_e_description);
        mBBack=findViewById(R.id.B_a_event_details_back);
    }

    private void showToast(String message)
    {
        Toast.makeText(EventDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
