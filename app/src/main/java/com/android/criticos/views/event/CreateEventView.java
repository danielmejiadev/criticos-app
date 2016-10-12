package com.android.criticos.views.event;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.android.criticos.R;
import com.android.criticos.app.RecyclerViewAdapterActors;
import com.android.criticos.models.Actor;
import com.android.criticos.models.Event;
import com.android.criticos.models.User;
import com.android.criticos.presenters.event.CreateEventPresenter;
import com.android.criticos.views.HomeView;
import java.util.Calendar;
import java.util.TimeZone;

public class CreateEventView extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerViewAdapterActors.OnItemClickListener {

    private EditText eventNameText;
    private EditText eventPlaceText;
    private EditText eventDescriptionText;

    private EditText eventDateText;
    private EditText eventBeginTimeText;
    private EditText eventEndTimeText;

    private CreateEventPresenter createEventPresenter;

    private User userCreator;

    private RecyclerView recyclerView;
    private RecyclerViewAdapterActors recyclerViewAdapterActors;

    private SearchView searchView;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_event_view);

        this.eventNameText = (EditText) findViewById(R.id.eventName);
        this.eventPlaceText = (EditText) findViewById(R.id.eventPlaceText);
        this.eventDescriptionText = (EditText) findViewById(R.id.eventDescription);

        this.eventDateText = (EditText) findViewById(R.id.eventDateText);
        this.eventBeginTimeText = (EditText) findViewById(R.id.eventBeginTime);
        this.eventEndTimeText = (EditText) findViewById(R.id.eventEndTime);

        this.createEventPresenter = new CreateEventPresenter(this);

        this.userCreator = (User) getIntent().getExtras().getSerializable("user");

        Actor actor = new Actor();
        actor.setUser(userCreator);
        actor.setRol("Creador");

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewAdapterActors = new RecyclerViewAdapterActors(actor);
        this.recyclerView.setAdapter(recyclerViewAdapterActors);
        this.recyclerViewAdapterActors.setClickListener(this);

        this.event = new Event();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_event_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    //Methods of buttons
    public void clickSaveEventButton(View v)
    {
        event.setEventName(eventNameText.getText().toString());
        event.setEventPlace(eventPlaceText.getText().toString());
        event.setEventDescription(eventDescriptionText.getText().toString());
        event.setEventActors(recyclerViewAdapterActors.getActors());
        createEventPresenter.saveEventAction(event);
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        createEventPresenter.searchActorByEmail(query, recyclerViewAdapterActors.getActors());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onLongClickItem(View view, int position) {
        createEventPresenter.longClickAction(view, position);
    }

    public void clickEventDateText(View v) {
        createEventPresenter.pickDateEventAction(v);
    }

    public void clickEventBeginTimeText(View v) {
        createEventPresenter.pickBeginTimeAction(v);
    }

    public void clickEventEndTimeText(View v) {
        createEventPresenter.pickEndTimeAction(v);
    }


    //Action in CreateEventView
    public void showPopupMenu(View view, final int position) {
        PopupMenu popup = new PopupMenu(CreateEventView.this, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu_actor, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        if (position == 0) {
                            showMessage("No se puede eliminar el creador del evento");
                        } else {
                            deleteActor(position);
                        }
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    public void putEventDate(View view, int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                try {

                    StringBuilder dateSave = new StringBuilder();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    SimpleDateFormat dateFormatToShow = new SimpleDateFormat("dd-mm-yyy");

                    dateSave.append(year);
                    dateSave.append("-");
                    dateSave.append(month+1);
                    dateSave.append("-");
                    dateSave.append(day);

                    event.setEventDate(dateFormat.parse(dateSave.toString()));
                    eventDateText.setText(dateFormatToShow.format(event.getEventDate()));
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void putBeginTimeEvent(View view, int hour, int minute) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute)
            {
                try {

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat timeFormatToShow = new SimpleDateFormat("hh:mm a");

                    StringBuilder timeSave = new StringBuilder();
                    timeSave.append(hour);
                    timeSave.append(":");
                    timeSave.append(minute);

                    event.setEventBeginTime(timeFormat.parse(timeSave.toString()));
                    eventBeginTimeText.setText(timeFormatToShow.format(event.getEventBeginTime()));
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void putEndTimeEvent(View view, int hour, int minute)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute)
            {
                try {

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat timeFormatToShow = new SimpleDateFormat("hh:mm a");

                    StringBuilder timeSave = new StringBuilder();
                    timeSave.append(hour);
                    timeSave.append(":");
                    timeSave.append(minute);

                    event.setEventEndTime(timeFormat.parse(timeSave.toString()));
                    eventEndTimeText.setText(timeFormatToShow.format(event.getEventEndTime()));
                } catch (ParseException e)
                {
                   e.printStackTrace();
                }
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void addActor(User user)
    {
        final Actor actor = new Actor();
        actor.setUser(user);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Asignar rol a actor");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actor.setRol(input.getText().toString());
                recyclerViewAdapterActors.addActor(actor);

            }
        });
        builder.setNegativeButton("Default Rol", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                actor.setRol("Actor");
                recyclerViewAdapterActors.addActor(actor);
            }
        });
        builder.show();
        searchView.setQuery("", false);
    }

    public void deleteActor(int position)
    {
        recyclerViewAdapterActors.deleteActor(position);
    }

    public void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showErrorEventName()
    {
        eventNameText.setError("Campo obligatorio");
    }

    public void showErrorEventPlace()
    {
        eventPlaceText.setError("Campo obligatorio");
    }

    public void showErrorEventDate() {
        eventDateText.setError("Campo obligatorio");
    }

    public void showErrorEventBeginTime() {
        eventBeginTimeText.setError("Campo obligatorio");
    }

    public void showErrorEventEndTime()
    {
        eventEndTimeText.setError("Campo obligatorio");
    }

    public void backToHomeView()
    {
        Intent intent = new Intent(this, HomeView.class);
        intent.putExtra("user", userCreator);
        startActivity(intent);
    }
}
