package com.android.criticos.views.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.criticos.R;
import com.android.criticos.models.User;
import com.android.criticos.presenters.user.UserProfilePresenter;
import com.android.criticos.views.HomeView;

public class UserProfileView extends AppCompatActivity {

    private UserProfilePresenter userProfilePresenter;
    private User userProfile;
    private EditText editNameText;
    private EditText editLastNameText;
    private EditText editOccupationText;
    private EditText editEmailText;
    private EditText editPasswordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        this.userProfilePresenter=new UserProfilePresenter(this);
        this.userProfile = (User)getIntent().getExtras().getSerializable("user");

        this.editNameText=(EditText)findViewById(R.id.editNameText);
        this.editLastNameText=(EditText)findViewById(R.id.editLastNameText);
        this.editOccupationText=(EditText)findViewById(R.id.editOccupationText);
        this.editEmailText=(EditText)findViewById(R.id.editEmailText);
        this.editPasswordText=(EditText)findViewById(R.id.editPasswordText);

        this.editNameText.setText(userProfile.getUserName());
        this.editLastNameText.setText(userProfile.getUserLastName());
        this.editOccupationText.setText(userProfile.getUserOccupation());
        this.editEmailText.setText(userProfile.getUserEmail());
        this.editPasswordText.setText(userProfile.getUserPassword());

    }

    //Methods of buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                userProfilePresenter.backHomeAction(userProfile);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickDisableAccount(View v)
    {
        userProfilePresenter.disableAccountAction(userProfile);
    }

    public void clickSaveChangesButton(View v)
    {
        String userEmail = this.editEmailText.getText().toString();
        String userPassword = this.editPasswordText.getText().toString();
        String userName = this.editNameText.getText().toString();
        String userLastName = this.editLastNameText.getText().toString();
        String userOccupation = this.editOccupationText.getText().toString();
        String userPicture = "";
        boolean userState = userProfile.isUserState();
        User modifiedUser = new User(userEmail,userPassword,userName,userLastName,userOccupation,userPicture,userState);
        userProfilePresenter.saveChangesAction(userProfile,modifiedUser);
    }


    //Methods of actions in Sign In View
    public void backToHome(User user)
    {
        Intent intent = new Intent(this,HomeView.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void  backToLogin()
    {
        Intent intent = new Intent(this,LoginView.class);
        startActivity(intent);
    }

    public void showMessage(String message)
    {
        Toast messageToast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        messageToast.show();
    }

    public void showErrorEmail()
    {
        editEmailText.setError("Correo electronico no valido");
    }
}