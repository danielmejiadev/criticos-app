package com.android.criticos.presenters.event;

import android.view.View;

import com.android.criticos.app.Constantes;
import com.android.criticos.controllers.EventController;
import com.android.criticos.models.Actor;
import com.android.criticos.models.Event;
import com.android.criticos.models.User;
import com.android.criticos.views.event.CreateEventView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
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
import java.util.TimeZone;

/**
 * Created by Daniel on 02/10/2016.
 */
public class CreateEventPresenter {

    private CreateEventView createEventView;
    private EventController eventController;

    public CreateEventPresenter(CreateEventView createEventView)
    {
        this.createEventView = createEventView;
        this.eventController=new EventController(this);
    }

    public void saveEventAction(Event event)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String eventDate = null;
        String eventBeginTime = null;
        String eventEndTime = null;

        boolean allowSaveEvent=true;

        if(event.getEventName().isEmpty())
        {
            createEventView.showErrorEventName();
            allowSaveEvent=false;
        }

        if(event.getEventPlace().isEmpty())
        {
            createEventView.showErrorEventPlace();
            allowSaveEvent=false;

        }

        if(event.getEventDate()==null)
        {
            createEventView.showErrorEventDate();
            allowSaveEvent=false;
        }
        else
        {
            eventDate = dateFormat.format(event.getEventDate());
        }

        if(event.getEventBeginTime()==null)
        {
            createEventView.showErrorEventBeginTime();
            allowSaveEvent=false;
        }
        else
        {
            eventBeginTime = timeFormat.format(event.getEventBeginTime());
        }

        if(event.getEventEndTime()==null)
        {
            createEventView.showErrorEventEndTime();
            allowSaveEvent=false;
        }
        else
        {
            eventEndTime=timeFormat.format(event.getEventEndTime());
        }

        if(allowSaveEvent)
        {
            HashMap<String, Object> eventJson = new HashMap<>();
            eventJson.put("eventName",event.getEventName());
            eventJson.put("eventDescription",event.getEventDescription());
            eventJson.put("eventDate", eventDate );
            eventJson.put("eventBeginTime", eventBeginTime );
            eventJson.put("eventEndTime", eventEndTime);
            eventJson.put("eventPlace", event.getEventPlace());

            JSONArray jsonArray = new JSONArray();

            for (Actor actor : event.getEventActors())
            {
                HashMap<String, Object> actorJson = new HashMap<>();
                actorJson.put("userEmail",actor.getUser().getUserEmail());
                actorJson.put("rol",actor.getRol());
                JSONObject jsonObject = new JSONObject(actorJson);
                jsonArray.put(jsonObject);
            }

            eventJson.put("eventActors", jsonArray);
            JSONObject jObject = new JSONObject(eventJson);

            eventController.jsonObjectPostRequest(jObject,Constantes.INSERT_EVENT_URL,"CreateEventPresenter","CreateEvent");
        }
    }

    public void createEventSuccess(JSONObject jsonObject)
    {
        String  message;
        try {
            Boolean state = jsonObject.getBoolean("state");
            message = jsonObject.getString("message");
            if(state)
            {
                createEventView.backToHomeView();
            }
            createEventView.showMessage(message);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public void searchActorByEmail(String emailActor, ArrayList<Actor> actors)
    {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailActor).matches())
        {
            createEventView.showMessage("Correo electronico no valido");
        }
        else
        {
            boolean value=false;
            for (Actor actor: actors)
            {
                if(actor.getUser().getUserEmail().equals(emailActor))
                {
                    value=true;
                    break;
                }
            }

            if(value)
            {
                createEventView.showMessage("Ya se agrego el actor");
            }
            else
            {
                ArrayList emailArray = new ArrayList();
                emailArray.add(emailActor);
                JSONArray jsonArray = new JSONArray(emailArray);
                eventController.jsonArrayPostRequest(jsonArray,Constantes.SEARCH_ACTOR_BY_EMAIL_URL,"CreateEventPresenter","searchActorByEmail");
            }
        }
    }

    public void searchActorSuccess(JSONArray jsonArray)
    {
        try {
            if(jsonArray !=null && jsonArray.length()>0)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String userEmail=jsonObject.getString("useremail");
                String userPassword=jsonObject.getString("userpassword");
                String userName=jsonObject.getString("username");
                String userLastName=jsonObject.getString("userlastname");
                String userOccupation=jsonObject.getString("useroccupation");
                String userPicture=jsonObject.getString("userpicture");
                boolean userState=jsonObject.getInt("userstate")!=0;

                if(userState)
                {
                    User user = new User(userEmail,userPassword,userName,userLastName,userOccupation,userPicture,userState);
                    createEventView.addActor(user);
                }
                else
                {
                    createEventView.showMessage("El usuario esta inactivo");
                }
            }
            else
            {
                createEventView.showMessage("El usuario no existe");
            }
        }catch (Exception e)
        {
            createEventView.showMessage("Error al consultar usuario");
        }
    }


    public void volleyError(VolleyError error,String msg)
    {
        String message;
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
        createEventView.showMessage(message);
    }

    public void longClickAction(View view, int position)
    {
        createEventView.showPopupMenu(view,position);
    }

    public void pickDateEventAction(View view)
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        createEventView.putEventDate(view,year,month,day);
    }

    public void pickBeginTimeAction(View view)
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        createEventView.putBeginTimeEvent(view,hour,minute);
    }

    public void pickEndTimeAction(View view)
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        createEventView.putEndTimeEvent(view,hour,minute);
    }
}
