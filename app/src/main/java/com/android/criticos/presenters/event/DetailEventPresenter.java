package com.android.criticos.presenters.event;

import android.view.View;

import com.android.criticos.app.Constantes;
import com.android.criticos.controllers.EventController;
import com.android.criticos.models.Comment;
import com.android.criticos.models.Event;
import com.android.criticos.views.event.DetailEventView;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;

/**
 * Created by Daniel on 09/10/2016.
 */
public class DetailEventPresenter {

    private DetailEventView detailEventView;
    private EventController eventController;

    public DetailEventPresenter(DetailEventView detailEventView)
    {
        this.detailEventView=detailEventView;
        this.eventController=new EventController(this);
    }

    public void addCommentAction(String commentText)
    {
        if(commentText.isEmpty())
        {
            detailEventView.showCommentError();
        }
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateFormatToSave = dateFormat.format(new Date());

            try {

                Comment comment = new Comment();
                comment.setCommentDescription(commentText);
                comment.setCommentDate(dateFormat.parse(dateFormatToSave));
                detailEventView.addCommentToList(comment);

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void backToHomeAction(Event event, ArrayList<Comment> commentsToSave)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        JSONArray jsonArray = new JSONArray();

        for(Comment comment:commentsToSave)
        {
            HashMap<String, Object> commentJson = new HashMap<>();
            commentJson.put("commentDescription",comment.getCommentDescription());
            commentJson.put("commentDate",dateFormat.format(comment.getCommentDate()));

            JSONObject jsonObject = new JSONObject(commentJson);
            jsonArray.put(jsonObject);
        }

        HashMap<String, Object> commentsToSaveJson = new HashMap<>();
        commentsToSaveJson.put("eventId",event.getEventId());
        commentsToSaveJson.put("comments",jsonArray);

        JSONObject jsonObject = new JSONObject(commentsToSaveJson);

        eventController.jsonObjectPostRequest(jsonObject, Constantes.INSERT_COMMENTS_BY_EVENT_URL,"DetailEventPresenter","BackToHome");
    }

    public void backToHomeSuccess(JSONObject jsonObject)
    {
        try {
            if(jsonObject.getBoolean("state"))
            {
                detailEventView.backToHome();
                detailEventView.showMessage("Cambios guardados exitosamente");
            }
            else
            {
                detailEventView.showMessage("No se han podido guardar los comentarios");
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
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
        detailEventView.showMessage(message);
    }
}
