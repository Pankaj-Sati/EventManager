package com.assessmentproj.evemanager.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.assessmentproj.evemanager.HomeActivity;
import com.assessmentproj.evemanager.Models.EventModel;
import com.assessmentproj.evemanager.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

/*
    Manages all the events in the firebase database
 */
public class EventLab
{
    Context mContext; //Context of calling component
    DatabaseReference mFirebaseDatabase;
    FirebaseUser mFirebaseUser;

    public interface EventFetchCallbacks
    {
        void onEventListFetched(List<EventModel> eventList); //Callback for successful list fetch
    }

    private static final String LOG_TAG="EVENT_lAB";

    public EventLab(Context context)
    {
        mContext=context;
        mFirebaseDatabase= FirebaseDatabase.getInstance().getReference(); //Get reference of database
        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean addNewEvent(EventModel eventModel) {
        //Return false for failure and true when successful
        boolean isSuccessful = false;
        if (eventModel == null)
        {
            return isSuccessful;
        }
        String eventID=eventModel.getmEventID();
        mFirebaseDatabase.child(DatabaseConstants.EventsCollection.KEY).child(eventID).child(DatabaseConstants.EventsCollection.EventDetails.EVENT_NAME).setValue(eventModel.getmEventName());
        mFirebaseDatabase.child(DatabaseConstants.EventsCollection.KEY).child(eventID).child(DatabaseConstants.EventsCollection.EventDetails.EVENT_ID).setValue(eventModel.getmEventID());
        mFirebaseDatabase.child(DatabaseConstants.EventsCollection.KEY).child(eventID).child(DatabaseConstants.EventsCollection.EventDetails.EVENT_CREATEDBY).setValue(mFirebaseUser.getUid());
        mFirebaseDatabase.child(DatabaseConstants.EventsCollection.KEY).child(eventID).child(DatabaseConstants.EventsCollection.EventDetails.EVENT_DESCRIPTION).setValue(eventModel.getmEventDescription());
        mFirebaseDatabase.child(DatabaseConstants.EventsCollection.KEY).child(eventID).child(DatabaseConstants.EventsCollection.EventDetails.EVENT_LOCATION).setValue(eventModel.getmEventLocation());
        isSuccessful=true;

        return isSuccessful;

    }

    public void getAllEvents(final EventFetchCallbacks eventFetchCallbacks)
    {
        final List<EventModel> eventsList=new ArrayList<>();
        mFirebaseDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                eventsList.clear();
                Log.d(LOG_TAG,dataSnapshot.toString());
                DataSnapshot eventsSnapshot=dataSnapshot.child(DatabaseConstants.EventsCollection.KEY);
                Log.d(LOG_TAG,"Events:"+eventsSnapshot.toString());
                for(DataSnapshot eachEventSnapshot:eventsSnapshot.getChildren())
                {
                    Log.d(LOG_TAG,"Each Event:"+eachEventSnapshot.toString());
                    HashMap<String,String> eventObject= (HashMap<String, String>) eachEventSnapshot.getValue();
                    Log.d(LOG_TAG,"Hash:"+eventObject.toString());
                    EventModel eventModel=new EventModel();
                    eventModel.setmEventID(eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_ID));
                    eventModel.setmEventName(eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_NAME));
                    eventModel.setmEventLocation(eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_LOCATION));
                    Log.d(LOG_TAG,"Location:"+eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_LOCATION));
                    eventModel.setmEventDescription(eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_DESCRIPTION));
                    eventModel.setmEventCreatedBy(eventObject.get(DatabaseConstants.EventsCollection.EventDetails.EVENT_CREATEDBY));
                    eventsList.add(eventModel);
                }
                Log.d(LOG_TAG,"Final List:"+eventsList.size());
               eventFetchCallbacks.onEventListFetched(eventsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
