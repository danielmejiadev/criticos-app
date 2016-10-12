package com.android.criticos.views.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.criticos.R;
import com.android.criticos.models.User;
import com.android.criticos.presenters.user.LoginPresenter;
import com.android.criticos.views.HomeView;

public class LoginView extends Activity {

    //Attributes
    private EditText textEmailAddress, textPassword;
    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login_view);

        this.textEmailAddress=(EditText)findViewById(R.id.correo);
        this.textPassword=(EditText)findViewById(R.id.pass);
        this.progressDialog = new ProgressDialog(this);
        this.loginPresenter = new LoginPresenter(this);
    }

    //Methods of buttons
    public void clickLoginButton(View v)
    {
        loginPresenter.loginAction(textEmailAddress.getText().toString(), textPassword.getText().toString());
    }

    public void clickGoToRegisterUser(View v)
    {
        loginPresenter.goToRegisterUserAction();
    }


    //Methods of actions in LoginView
    public void goToRegisterUser()
    {
        startActivity(new Intent(this, SignInUserView.class));
    }

    public void goToHome(User user)
    {
        Intent intent = new Intent(this,HomeView.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void showProgress()
    {
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgress()
    {
        progressDialog.dismiss();
    }

    public void showErrorEmailUser(String error)
    {
        textEmailAddress.setError(error);
    }

    public void showErrorPasswordUser(String error)
    {
        textPassword.setError(error);
    }

    public void loginError(String error)
    {
        Toast errorMessage= Toast.makeText(this,error,Toast.LENGTH_LONG);
        errorMessage.show();
    }
}