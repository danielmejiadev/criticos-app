package com.android.criticos.presenters.user;

import com.android.criticos.controllers.UserController;
import com.android.criticos.models.User;
import com.android.criticos.views.user.SignInUserView;

/**
 * Created by Daniel on 28/09/2016.
 */
public class SignInPresenter {

    private SignInUserView signInUserView;
    private UserController userController;

    public SignInPresenter(SignInUserView signInUserView)
    {
        this.signInUserView = signInUserView;
        this.userController=new UserController(this);
    }

    public void signInUserAction(String userName, String userLastName,String userOccupation,String userEmail,String userPassword,String userPasswordRepeat)
    {
        boolean allowSignIn=true;

        if(userName.isEmpty())
        {
            signInUserView.showErrorNameUser("Nombre obligatorio");
            allowSignIn=false;
        }

        if(userLastName.isEmpty())
        {
            signInUserView.showErrorLastNameUser("Apellido obligatorio");
            allowSignIn=false;
        }

        if(userEmail.isEmpty())
        {
            signInUserView.showErrorEmailUser("Correo electronico obligatorio");
            allowSignIn=false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            signInUserView.showErrorEmailUser("Correo electronico no valido");
            allowSignIn=false;
        }

        if(userPassword.isEmpty() || userPasswordRepeat.isEmpty())
        {
            signInUserView.showErrorPasswordUser("Contraseña obligatoria");
            signInUserView.showErrorPasswordRepeatUser("Contraseña obligatoria");
            allowSignIn=false;
        }
        else if(!userPassword.equals(userPasswordRepeat))
        {
            signInUserView.showErrorPasswordRepeatUser("Contraseña no coincide");
            allowSignIn=false;
        }

        if(allowSignIn)
        {
            User user = new User(userEmail,userPassword,userName,userLastName,userOccupation,"",true);
            userController.signInUser(user);
        }

    }

    public void showSignInUserMessage(String message)
    {
        signInUserView.showSignInUserMessage(message);
    }

    public void signInSuccess(User user)
    {
        signInUserView.goToHome(user);
    }
}
