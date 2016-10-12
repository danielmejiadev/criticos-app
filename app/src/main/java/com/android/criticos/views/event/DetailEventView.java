package com.android.criticos.views.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.criticos.R;
import com.android.criticos.app.RecyclerViewAdapterActors;
import com.android.criticos.app.RecyclerViewAdapterComment;
import com.android.criticos.models.Comment;
import com.android.criticos.models.Event;
import com.android.criticos.models.User;
import com.android.criticos.presenters.event.DetailEventPresenter;
import com.android.criticos.views.HomeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailEventView extends AppCompatActivity {


    private Event event;

    private User userActive;

    private EditText eventCommentText;

    private RecyclerViewAdapterComment recyclerViewAdapterComment;

    private DetailEventPresenter detailEventPresenter;

    private ArrayList<Comment> commentsToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_view);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

        this.event = (Event)getIntent().getExtras().getSerializable("event");
        this.userActive = (User)getIntent().getExtras().getSerializable("user");

        TextView eventNameText = (TextView) findViewById(R.id.eventNameTitle);
        eventNameText.setText(event.getEventName());

        EditText eventPlaceText = (EditText)findViewById(R.id.eventPlaceText);
        eventPlaceText.setText("Lugar: "+event.getEventPlace());

        EditText eventDateText = (EditText)findViewById(R.id.eventDateText);
        eventDateText.setText(dateFormat.format(event.getEventDate()));

        EditText eventBeginTimeText = (EditText)findViewById(R.id.eventBeginTimeText);
        eventBeginTimeText.setText("Inicio: "+timeFormat.format(event.getEventBeginTime()));

        EditText eventEndTimeText = (EditText)findViewById(R.id.eventEndTimeText);
        eventEndTimeText.setText("Fin: "+timeFormat.format(event.getEventEndTime()));

        EditText eventDescriptionText = (EditText)findViewById(R.id.eventDescriptionText);
        eventDescriptionText.setText(event.getEventDescription());

        this.eventCommentText = (EditText)findViewById(R.id.eventCommentText);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reciclerViewActosEventDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewAdapterActors recyclerViewAdapterActors = new RecyclerViewAdapterActors(event.getEventActors());
        recyclerView.setAdapter(recyclerViewAdapterActors);

        RecyclerView recyclerViewComments = (RecyclerView) findViewById(R.id.commentList);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewComments.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewAdapterComment = new RecyclerViewAdapterComment(event.getEventComments());
        recyclerViewComments.setAdapter(recyclerViewAdapterComment);

        this.detailEventPresenter=new DetailEventPresenter(this);

        this.commentsToSave=new ArrayList<>();

        FloatingActionButton addCommentFloatingButton = (FloatingActionButton) findViewById(R.id.addCommentFloatingButton);
        addCommentFloatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                detailEventPresenter.addCommentAction(eventCommentText.getText().toString());
            }
        });

        FloatingActionButton backToHomeFloatingButton = (FloatingActionButton) findViewById(R.id.backToHomeFloatingButton);
        backToHomeFloatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                detailEventPresenter.backToHomeAction(event,commentsToSave);
            }
        });
    }

    public void addCommentToList(Comment comment)
    {
        commentsToSave.add(comment);
        recyclerViewAdapterComment.addComment(comment);
        eventCommentText.setText("");
        showMessage("Comentario agregado exitosamente");
    }

    public void backToHome()
    {
        Intent intent = new Intent(this, HomeView.class);
        intent.putExtra("user",userActive);
        startActivity(intent);
    }

    public void showCommentError()
    {
        eventCommentText.setError("Por favor ingrese un comentario");
    }

    public void showMessage(String message)
    {
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }
}