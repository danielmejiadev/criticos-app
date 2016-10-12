package com.android.criticos.controllers;

import com.android.criticos.app.ApplicationController;
import com.android.criticos.presenters.HomePresenter;
import com.android.criticos.presenters.event.CreateEventPresenter;
import com.android.criticos.presenters.event.DetailEventPresenter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 02/10/2016.
 */
public class EventController {

    private CreateEventPresenter createEventPresenter;

    private HomePresenter homePresenter;

    private DetailEventPresenter detailEventPresenter;


    public EventController(CreateEventPresenter createEventPresenter)
    {
        this.createEventPresenter = createEventPresenter;
    }

    public EventController(HomePresenter homePresenter)
    {
        this.homePresenter=homePresenter;
    }

    public EventController(DetailEventPresenter detailEventPresenter)
    {
        this.detailEventPresenter=detailEventPresenter;
    }

    public void jsonArrayPostRequest(JSONArray jsonArray, String url, final String presenter, final String typeRequest)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        switch (presenter)
                        {
                            case "CreateEventPresenter":
                                switch (typeRequest)
                                {
                                    case "searchActorByEmail":
                                        createEventPresenter.searchActorSuccess(response);
                                        break;
                                }
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        switch (presenter)
                        {
                            case "CreateEventPresenter":
                                createEventPresenter.volleyError(error,"Error al crear el usuario");
                                break;
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8" + getParamsEncoding();
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    public void jsonObjectPostRequest(JSONObject jsonObject, String url, final String presenter, final String typeRequest)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        switch (presenter)
                        {
                            case "CreateEventPresenter":
                                switch (typeRequest)
                                {
                                    case "CreateEvent":
                                        createEventPresenter.createEventSuccess(response);
                                        break;
                                }
                                break;

                            case "HomePresenter":
                                switch (typeRequest)
                                {
                                    case "SearchEventByName":
                                        homePresenter.searchEventByNameSuccess(response);
                                        break;
                                    case "LoadInformationEvent":
                                        homePresenter.loadInformationEventSuccess(response);
                                        break;
                                    case "ShowEventList":
                                        homePresenter.showEventListSuccess(response);
                                        break;
                                }
                                break;

                            case "DetailEventPresenter":
                                switch (typeRequest)
                                {
                                    case "BackToHome":
                                        detailEventPresenter.backToHomeSuccess(response);
                                        break;
                                }
                                break;

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        switch (presenter)
                        {
                            case "CreateEventPresenter":
                                switch (typeRequest)
                                {
                                    case "CreateEvent":
                                        createEventPresenter.volleyError(error,"Error al crear evento");
                                        break;
                                }
                                break;

                            case "HomePresenter":
                                switch (typeRequest)
                                {
                                    case "SearchEventByName":
                                        homePresenter.volleyError(error,"Error al realizar la busqueda");
                                        break;
                                    case "LoadInformationEvent":
                                        homePresenter.volleyError(error,"Error al cargar los datos del evento");
                                        break;
                                    case "ShowEventList":
                                        homePresenter.volleyError(error,"Error al cargar los eventos");
                                        break;
                                }
                                break;

                            case "DetailEventPresenter":
                                switch (typeRequest)
                                {
                                    case "BackToHome":
                                        detailEventPresenter.volleyError(error,"No se han podido guardar los comentarios");
                                        break;
                                }
                                break;
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8" + getParamsEncoding();
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
