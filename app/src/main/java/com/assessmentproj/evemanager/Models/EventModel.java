package com.assessmentproj.evemanager.Models;

import java.util.Date;
import java.util.UUID;

/*
    Holds a single instance of Event
 */
public class EventModel
{
    public String mEventName;
    public String mEventID;
    public String mEventDescription;
    public String mEventCreatedBy;
    public String mEventLocation;

    public String getmEventLocation() {
        return mEventLocation;
    }

    public void setmEventLocation(String mEventLocation) {
        this.mEventLocation = mEventLocation;
    }

    public EventModel()
    {
        mEventID= UUID.randomUUID().toString();
    }
    public String getmEventName() {
        return mEventName;
    }

    public void setmEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getmEventID() {
        return mEventID;
    }

    public void setmEventID(String mEventID) {
        this.mEventID = mEventID;
    }

    public String getmEventDescription() {
        return mEventDescription;
    }

    public void setmEventDescription(String mEventDescription) {
        this.mEventDescription = mEventDescription;
    }

    public String getmEventCreatedBy() {
        return mEventCreatedBy;
    }

    public void setmEventCreatedBy(String mEventCreatedBy) {
        this.mEventCreatedBy = mEventCreatedBy;
    }


}
