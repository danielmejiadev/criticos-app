package com.android.criticos.controllers;

import com.android.criticos.models.User;
import com.android.criticos.presenters.user.LoginPresenter;
import com.android.criticos.app.ApplicationController;
import com.android.criticos.app.Constantes;
import com.android.criticos.presenters.user.SignInPresenter;
import com.android.criticos.presenters.user.UserProfilePresenter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 26/09/2016.
 */
public class UserController{

    private LoginPresenter loginPresenter;
    private SignInPresenter signInPresenter;
    private UserProfilePresenter userProfilePresenter;

    public UserController(LoginPresenter loginPresenter)
    {
        this.loginPresenter = loginPresenter;
    }

    public UserController(SignInPresenter signInPresenter)
    {
        this.signInPresenter = signInPresenter;
    }

    public UserController(UserProfilePresenter userProfilePresenter)
    {
        this.userProfilePresenter=userProfilePresenter;
    }

    public void toValidateLogin(String email, String password)
    {
        String url = Constantes.LOGIN_URL +"?user="+email+"&password="+password;

        JsonArrayRequest requestJson = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray)
                    {
                        try{
                            if(jsonArray!=null && jsonArray.length()>0)
                            {
                                JSONObject jsonObject= jsonArray.getJSONObject(0);

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
                                   loginPresenter.loginSuccess(user);
                                }
                                else
                                {
                                   loginPresenter.loginError("la cuenta "+userEmail+ " esta inactiva");
                                }
                            }
                            else
                            {
                                loginPresenter.loginError("Datos incorrectos");
                            }
                        }catch (Exception e)
                        {
                            loginPresenter.loginError("Error al intentar verificar la informacion");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                loginPresenter.loginError("Error de conexion");
            }
        }
        );
        ApplicationController.getInstance().addToRequestQueue(requestJson);
    }

    public void signInUser(final User user)
    {
        HashMap<String, Object> map = new HashMap<>();

        map.put("userEmail", user.getUserEmail());
        map.put("userPassword", user.getUserPassword());
        map.put("userName", user.getUserName());
        map.put("userLastName", user.getUserLastName());
        map.put("userOccupation", user.getUserOccupation());
        map.put("userPicture", user.getUserPicture());
        map.put("userState", user.isUserState());

        JSONObject jObject = new JSONObject(map);

        final User newUser=user;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.SIGN_IN_URL,
                jObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String message;
                        try {
                            message = response.getString("message");
                            signInPresenter.showSignInUserMessage(message);
                            if(response.getBoolean("state"))
                            {
                                signInPresenter.signInSuccess(newUser);
                            }
                        } catch (JSONException e)
                        {
                            signInPresenter.showSignInUserMessage("Error al registrar usuario...");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        signInPresenter.showSignInUserMessage("Error al registrar usuario");
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


    public void modifiedUser(User user, boolean type,String userEmailOld)
    {
        HashMap<String, Object> map = new HashMap<>();

        map.put("userEmailNew", user.getUserEmail());
        map.put("userEmailOld", userEmailOld);
        map.put("userPassword", user.getUserPassword());
        map.put("userName", user.getUserName());
        map.put("userLastName", user.getUserLastName());
        map.put("userOccupation", user.getUserOccupation());
        map.put("userPicture", user.getUserPicture());
        map.put("userState", user.isUserState());

        JSONObject jObject = new JSONObject(map);

        final User userModified = user;
        final Boolean typeValue = type;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.MODIFIED_USER_URL,
                jObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String message;
                        try {

                            if(typeValue)
                            {
                                if(response.getBoolean("state"))
                                {
                                    userProfilePresenter.backHomeAction(userModified);
                                }
                                message = response.getString("message");
                                userProfilePresenter.showMessage(message);
                            }
                            else
                            {
                                userProfilePresenter.showMessage("Cuenta desactivada");
                                userProfilePresenter.backToLoginAction();
                            }
                        } catch (JSONException e)
                        {
                            userProfilePresenter.showMessage("Error al actualizar el usuario");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(typeValue)
                        {
                            userProfilePresenter.backHomeAction(userModified);
                            userProfilePresenter.showMessage("Error al actualizar el usuario");
                        }
                        else
                        {
                            userProfilePresenter.backToLoginAction();
                            userProfilePresenter.showMessage("Error al desactivar la cuenta");
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
