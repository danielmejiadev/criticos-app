package com.android.criticos.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.criticos.R;
import com.android.criticos.app.DelayAutoCompleteTextView;
import com.android.criticos.app.ListViewAdapterEvents;
import com.android.criticos.models.Actor;
import com.android.criticos.models.Comment;
import com.android.criticos.models.Event;
import com.android.criticos.models.User;
import com.android.criticos.presenters.HomePresenter;
import com.android.criticos.views.event.CreateEventView;
import com.android.criticos.views.event.DetailEventView;
import com.android.criticos.views.user.LoginView;
import com.android.criticos.views.user.UserProfileView;

import java.util.ArrayList;

public class HomeView extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private User activeUser;

    private HomePresenter homePresenter;

    private ListViewAdapterEvents listViewAdapterEvents;

    private DelayAutoCompleteTextView delayAutoCompleteTextView;

    private Event event;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home_view);

        this.activeUser = (User)getIntent().getExtras().getSerializable("user");

        this.listViewAdapterEvents=new ListViewAdapterEvents();

        this.homePresenter=new HomePresenter(this,listViewAdapterEvents);

        this.listViewAdapterEvents.setHomePresenter(homePresenter);

        this.setSupportActionBar((Toolbar)findViewById(R.id.toolbarHome));

        this.delayAutoCompleteTextView= (DelayAutoCompleteTextView)findViewById(R.id.delayEditText);
        this.delayAutoCompleteTextView.setThreshold(4);
        this.delayAutoCompleteTextView.setAdapter(listViewAdapterEvents);
        this.delayAutoCompleteTextView.setOnItemClickListener(this);

        this.progressDialog = new ProgressDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        event = listViewAdapterEvents.getItem(i);
        delayAutoCompleteTextView.setText(event.getEventName());
        homePresenter.loadInformationEvent(event.getEventId());
    }

    public void goToEventDetailView(ArrayList<Actor> eventActors, ArrayList<Comment> eventComments)
    {
        event.setEventComments(eventComments);
        event.setEventActors(eventActors);
        Intent intent = new Intent(this, DetailEventView.class);
        intent.putExtra("event",event);
        intent.putExtra("user",activeUser);
        startActivity(intent);
        delayAutoCompleteTextView.setText("");
    }

    public void clickGoToUserProfileButton(MenuItem item)
    {
        homePresenter.goToUserProfileAction();
    }

    public void clickSignOutButton(MenuItem item)
    {
        homePresenter.signOutAction();
    }

    public void clickGoToCreateEvent(View v)
    {
        homePresenter.goToCreateEventAction();
    }

    public void clickShowEventListLikeCreator(View view)
    {
        homePresenter.showEventList(activeUser,true);
    }

    public void clickShowEventListLikeActor(View view)
    {
        homePresenter.showEventList(activeUser,false);
    }

    public void showEventListDialog(ArrayList<Event> eventsToShow)
    {

        if(eventsToShow.isEmpty())
        {
            showMessage("No tiene eventos para mostrar");
        }
        else
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Eventos");
            dialogBuilder.setCancelable(true);
            dialogBuilder.setPositiveButton("Ok",null);
            ListViewAdapterEvents listViewAdapterEvents = new ListViewAdapterEvents(eventsToShow);
            dialogBuilder.setAdapter(listViewAdapterEvents,null);
            AlertDialog alertDialogObject = dialogBuilder.create();
            alertDialogObject.show();
        }

    }


    //Methods of actions in Sign In View
    public void goToUserProfile()
    {
        Intent intent = new Intent(this,UserProfileView.class);
        intent.putExtra("user",activeUser);
        startActivity(intent);
    }

    public void signOut()
    {
        Intent intent = new Intent(this,LoginView.class);
        startActivity(intent);
    }

    public void goToCreateEvent()
    {
        Intent intent = new Intent(this,CreateEventView.class);
        intent.putExtra("user",activeUser);
        startActivity(intent);
    }


    public void showProgress()
    {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgress()
    {
        progressDialog.dismiss();
    }

    public void showMessage(String message)
    {
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }
}
