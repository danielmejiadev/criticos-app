package com.android.criticos.presenters.user;

import com.android.criticos.controllers.UserController;
import com.android.criticos.models.User;
import com.android.criticos.views.user.LoginView;

/**
 * Created by Daniel on 26/09/2016.
 */
public class LoginPresenter {

    private LoginView loginView;
    private UserController userController;

    public LoginPresenter(LoginView userView)
    {
        this.loginView = userView;
        this.userController = new UserController(this);
    }

    public void loginAction(String email, String password)
    {
        if (email.isEmpty())
        {
            loginView.showErrorEmailUser("Correo obligatorio");
        }
        else
        {
            if (password.isEmpty())
            {
                loginView.showErrorPasswordUser("Contraseña obligatoria");
            }
            else
            {
                loginView.showProgress();
                userController.toValidateLogin(email, password);
            }
        }
    }

    public void goToRegisterUserAction()
    {
        loginView.goToRegisterUser();
    }

    public void loginError(String error)
    {
        loginView.hideProgress();
        loginView.loginError(error);
    }

    public void loginSuccess(User user)
    {
        loginView.hideProgress();
        loginView.goToHome(user);
    }
}