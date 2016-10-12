package com.android.criticos.presenters;

import android.util.Log;
import android.widget.Toast;

import com.android.criticos.app.Constantes;
import com.android.criticos.app.ListViewAdapterEvents;
import com.android.criticos.controllers.EventController;
import com.android.criticos.models.Actor;
import com.android.criticos.models.Comment;
import com.android.criticos.models.Event;
import com.android.criticos.models.User;
import com.android.criticos.views.HomeView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Daniel on 30/09/2016.
 */
public class HomePresenter {

    private HomeView homeView;

    private ArrayList<Event> events;

    private ListViewAdapterEvents listViewAdapterEvents;

    private EventController eventController=new EventController(this);

    public HomePresenter(HomeView homeView,ListViewAdapterEvents listViewAdapterEvents)
    {
        this.homeView = homeView;
        this.listViewAdapterEvents=listViewAdapterEvents;
    }

    public void searchEventByName(String nameEvent)
    {
        this.events=new ArrayList<>();

        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        Date date = c.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String currentDate= dateFormat.format(date);

        HashMap<String, Object> eventJson = new HashMap<>();
        eventJson.put("eventName",nameEvent);
        eventJson.put("currentDate",currentDate);

        JSONObject jsonObject = new JSONObject(eventJson);

        eventController.jsonObjectPostRequest(jsonObject, Constantes.SEARCH_EVENT_BY_NAME_URL,"HomePresenter","SearchEventByName");
    }

    public void searchEventByNameSuccess(JSONObject jsonObject)
    {
        try {
            JSONArray events = jsonObject.getJSONArray("events");

            for (int i=0; i<events.length();i++)
            {
                JSONObject jsonEvent = events.getJSONObject(i);

                int eventId=jsonEvent.getInt("eventid");
                String eventName=jsonEvent.getString("eventname");
                String eventDescription=jsonEvent.getString("eventdescription");
                String eventDate=jsonEvent.getString("eventdate");
                String eventBeginTime=jsonEvent.getString("eventbegintime");
                String eventEndTime=jsonEvent.getString("eventendtime");
                String eventPlace=jsonEvent.getString("eventplace");
                int eventCalification=jsonEvent.getInt("eventcalification");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd",Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());

                Event event = new Event();
                event.setEventId(eventId);
                event.setEventName(eventName);
                event.setEventDescription(eventDescription);
                event.setEventDate(dateFormat.parse(eventDate));
                event.setEventBeginTime(timeFormat.parse(eventBeginTime));
                event.setEventEndTime(timeFormat.parse(eventEndTime));
                event.setEventPlace(eventPlace);
                event.setEventCalification(eventCalification);

                this.events.add(event);

            }

            listViewAdapterEvents.setRequestFinished(true);

        } catch (JSONException e)
        {
            e.printStackTrace();
            homeView.showMessage("Error al realizar la busqueda");

        } catch (ParseException e)
        {
            e.printStackTrace();
            homeView.showMessage("Error al realizar la busqueda");

        }

    }

    public void loadInformationEvent(int eventId)
    {
        HashMap<String,Object> eventJson = new HashMap<>();
        eventJson.put("eventId",eventId);
        JSONObject jsonObject = new JSONObject(eventJson);
        homeView.showProgress();
        eventController.jsonObjectPostRequest(jsonObject, Constantes.LOAD_INFORMATION_EVENT_URL,"HomePresenter","LoadInformationEvent");
    }

    public void loadInformationEventSuccess(JSONObject jsonObject)
    {
        try {

            JSONArray actors = jsonObject.getJSONArray("actors");
            JSONArray comments = jsonObject.getJSONArray("comments");

            ArrayList<Actor> eventActors= new ArrayList<>();
            ArrayList<Comment> eventComments = new ArrayList<>();

            for(int i=0;i<actors.length();i++)
            {
                JSONObject jsonActor = actors.getJSONObject(i);

                String userEmail = jsonActor.getString("useremail");
                String userPassword = jsonActor.getString("userpassword");
                String userName = jsonActor.getString("username");
                String userLastName = jsonActor.getString("userlastname");
                String userOccupation = jsonActor.getString("useroccupation");
                String userPicture = jsonActor.getString("userpicture");
                boolean userState=jsonActor.getInt("userstate")!=0;
                User user = new User(userEmail,userPassword,userName,userLastName,userOccupation,userPicture,userState);

                String actorRol = jsonActor.getString("rol");
                int actorCalification = jsonActor.getInt("calification");

                Actor actor = new Actor(user,null,actorRol,actorCalification);
                eventActors.add(actor);
            }

            for(int i=0;i<comments.length();i++)
            {
                JSONObject jsonComment = comments.getJSONObject(i);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd",Locale.getDefault());

                int commentId = jsonComment.getInt("commentid");
                String commentDescription = jsonComment.getString("commentdescription");
                Date commentDate = dateFormat.parse(jsonComment.getString("commentdate"));

                Comment comment = new Comment();
                comment.setCommentId(commentId);
                comment.setCommentDescription(commentDescription);
                comment.setCommentDate(commentDate);

                eventComments.add(comment);
            }

            homeView.goToEventDetailView(eventActors,eventComments);
            homeView.hideProgress();

        } catch (JSONException e)
        {
            e.printStackTrace();

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

    }

    public void showEventList(User user,boolean creator)
    {
        HashMap<String,Object> userJson = new HashMap<>();
        userJson.put("userEmail",user.getUserEmail());
        JSONObject jsonObject = new JSONObject(userJson);
        if(creator)
        {
            eventController.jsonObjectPostRequest(jsonObject, Constantes.LOAD_EVENT_BY_USER_LIKE_CREATOR_URL,"HomePresenter","ShowEventList");
        }
        else
        {
            eventController.jsonObjectPostRequest(jsonObject, Constantes.LOAD_EVENT_BY_USER_LIKE_ACTOR_URL,"HomePresenter","ShowEventList");
        }
    }

    public void showEventListSuccess(JSONObject jsonObject)
    {
        try {
            JSONArray events = jsonObject.getJSONArray("events");

            ArrayList<Event> eventsToShow = new ArrayList<>();

            for(int i=0;i<events.length();i++)
            {
                JSONObject jsonEvent = events.getJSONObject(i);

                String eventName=jsonEvent.getString("eventname");
                String eventDescription=jsonEvent.getString("eventdescription");

                Event event = new Event();
                event.setEventName(eventName);
                event.setEventDescription(eventDescription);

                eventsToShow.add(event);
            }

            homeView.showEventListDialog(eventsToShow);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }


    public void volleyError(VolleyError error,String msg)
    {
        String message;
        if(msg.equals("Error al realizar la busqueda"))
        {
            listViewAdapterEvents.setRequestFinished(true);
        }

        if (error instanceof TimeoutError)
        {
            message="Demasiado tiempo para la conexion";
        }
        else if (error instanceof NoConnectionError)
        {
            message="Error de conexion";
        }
        else if (error instanceof AuthFailureError)
        {
            message="Error de autenticacion";
        }
        else if (error instanceof ServerError)
        {
            message="Error en el servidor";
        }
        else if (error instanceof NetworkError)
        {
            message="Error de red";
        }
        else
        {
            message=msg;
        }
        homeView.showMessage(message);
    }

    public void goToUserProfileAction()
    {
        homeView.goToUserProfile();
    }

    public void signOutAction()
    {
        homeView.signOut();
    }

    public void goToCreateEventAction()
    {
        homeView.goToCreateEvent();
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }


    public void s(String s)
    {
        homeView.showMessage(s);
    }
}
