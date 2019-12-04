package com.assessmentproj.evemanager;

import android.content.Intent;
import android.os.Bundle;

import com.assessmentproj.evemanager.Models.EventModel;
import com.assessmentproj.evemanager.data.EventLab;
import com.assessmentproj.evemanager.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends MainHostingActivity
{
    //View
    RecyclerView mRVEventsList;
    ProgressBar mProgressBar;

    //Data
    EventLab mEventLab; //For managing events in database
    List<EventModel> mEventList=null; //To store events
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mProgressBar=findViewById(R.id.PB_a_home);

        mFirebaseAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(HomeActivity.this,AddEventActivity.class);
                startActivity(intent);

            }
        });
        mRVEventsList=findViewById(R.id.RV_a_home_events_list);
        mRVEventsList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        updateRecyclerView();

        mEventLab=new EventLab(HomeActivity.this);
        mEventLab.getAllEvents(new EventLab.EventFetchCallbacks() {
            @Override
            public void onEventListFetched(List<EventModel> eventList)
            {
                mProgressBar.setVisibility(View.GONE);
                Log.d("HomeActivity","ListSize="+eventList.size());
                mEventList=eventList;
                updateRecyclerView();
            }
        });
        //Log.d("HomeActivity",mEventList.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            mFirebaseAuth.signOut();
            Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RVEventListViewHolder extends RecyclerView.ViewHolder
    {
        TextView eventName;
        EventModel event;
        public RVEventListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            eventName=itemView.findViewById(R.id.TV_rv_event_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i=EventDetailsActivity.createIntent(HomeActivity.this,event);
                    startActivity(i);
                }
            });
        }

        public void bindDataToView(EventModel eventModel)
        {
            event=eventModel;
          eventName.setText(eventModel.getmEventName());
        }
    }

    public class RVEventListAdapter extends RecyclerView.Adapter<RVEventListViewHolder>
    {

        @NonNull
        @Override
        public RVEventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View itemView=LayoutInflater.from(HomeActivity.this).inflate(R.layout.rv_event_item,parent,false);
            return new RVEventListViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RVEventListViewHolder holder, int position)
        {
            holder.bindDataToView(mEventList.get(position));
        }

        @Override
        public int getItemCount()
        {
            if(mEventList==null)
            {
                showToast("No data found");
                return 0;
            }
            return mEventList.size();
        }
    }

    public void updateRecyclerView()
    {
        if(mRVEventsList.getAdapter()==null)
        {
            mRVEventsList.setAdapter(new RVEventListAdapter());
        }
        else
        {
            mRVEventsList.getAdapter().notifyDataSetChanged(); //Update layout
        }
    }

    public void showToast(String message)
    {
        Toast.makeText(HomeActivity.this,message,Toast.LENGTH_SHORT).show();
    }

}
