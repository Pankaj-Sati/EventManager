package com.assessmentproj.evemanager.data;
/*
    Database schema and other constants
 */
public class DatabaseConstants
{
    public static final class UsersCollection
    {
        public static final String KEY="users";
        public static final class UserElements
        {
            public static final String EMAIL="user_eamil";
            public static final String ADDRESS="user_address";
            public static final String ID="user_id";
            public static final String NAME="user_name";
        }
    }

    public static final class EventsCollection
    {
        public static final String KEY="events";
        public static final class EventDetails
        {
            public static final String EVENT_NAME="ev_name";
            public static final String EVENT_LOCATION="ev_location";
            public static final String EVENT_ID="ev_id";
            public static final String EVENT_DATE="ev_date";
            public static final String EVENT_DESCRIPTION="ev_description";
            public static final String EVENT_CREATEDBY="ev_created_by";
        }
    }
}
