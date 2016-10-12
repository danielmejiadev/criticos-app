package com.android.criticos.views.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.criticos.R;
import com.android.criticos.models.User;
import com.android.criticos.presenters.user.SignInPresenter;
import com.android.criticos.views.HomeView;

public class SignInUserView extends AppCompatActivity{

    private EditText userNameText;
    private EditText userLastNameText;
    private EditText userOccupationText;
    private EditText userEmailText;
    private EditText userPasswordText;
    private EditText userPassword2Text;
    private SignInPresenter signInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in_user_view);

        this.userNameText=(EditText)findViewById(R.id.userNameText);
        this.userLastNameText=(EditText)findViewById(R.id.userLastNameText);
        this.userOccupationText=(EditText)findViewById(R.id.userOccupationText);
        this.userEmailText=(EditText)findViewById(R.id.userEmailText);
        this.userPasswordText=(EditText)findViewById(R.id.userPasswordText);
        this.userPassword2Text =(EditText)findViewById(R.id.userPassword2Text);

        this.signInPresenter =new SignInPresenter(this);
    }

    //Methods of buttons
    public void clickSignInUserButton(View v)
    {
        String userName = userNameText.getText().toString();
        String userLastName = userLastNameText.getText().toString();
        String userOccupation = userOccupationText.getText().toString();
        String userEmail = userEmailText.getText().toString();
        String userPassword = userPasswordText.getText().toString();
        String userPasswordRepeat = userPassword2Text.getText().toString();
        signInPresenter.signInUserAction(userName, userLastName, userOccupation, userEmail, userPassword, userPasswordRepeat);
    }


    //Methods of actions in Sign In View
    public void goToHome(User user)
    {
        Intent intent = new Intent(this,HomeView.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void showSignInUserMessage(String message)
    {
        Toast toastMessage = Toast.makeText(this,message,Toast.LENGTH_LONG);
        toastMessage.show();
    }

    public void showErrorNameUser(String error)
    {
        userNameText.setError(error);
    }

    public void showErrorLastNameUser(String error)
    {
        userLastNameText.setError(error);
    }

    public void showErrorEmailUser(String error)
    {
        userEmailText.setError(error);
    }

    public void showErrorPasswordUser(String error)
    {
        userPasswordText.setError(error);
    }

    public void showErrorPasswordRepeatUser(String error)
    {
        userPassword2Text.setError(error);
    }
}