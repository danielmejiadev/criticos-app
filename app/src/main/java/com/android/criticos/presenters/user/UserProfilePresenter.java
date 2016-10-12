package com.android.criticos.presenters.user;

import com.android.criticos.controllers.UserController;
import com.android.criticos.models.User;
import com.android.criticos.views.user.UserProfileView;

/**
 * Created by Daniel on 30/09/2016.
 */
public class UserProfilePresenter {

    private UserProfileView userProfileView;
    private UserController userController;

    public UserProfilePresenter(UserProfileView userProfileView)
    {
        this.userProfileView = userProfileView;
        this.userController=new UserController(this);
    }

    public void backHomeAction(User user)
    {
        userProfileView.backToHome(user);
    }

    public void backToLoginAction()
    {
        userProfileView.backToLogin();
    }

    public void disableAccountAction(User user)
    {
        user.setUserState(false);
        userController.modifiedUser(user,false,user.getUserEmail());
    }

    public void saveChangesAction(User userProfile, User modifiedUser)
    {
        if(userProfile.equals(modifiedUser))
        {
            userProfileView.showMessage("No se realizado ninguna modificacion");
            userProfileView.backToHome(userProfile);
        }
        else
        {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(modifiedUser.getUserEmail()).matches())
            {
                userProfileView.showErrorEmail();
            }
            else
            {
                userController.modifiedUser(modifiedUser, true, userProfile.getUserEmail());
            }
        }
    }

    public void showMessage(String message)
    {
        userProfileView.showMessage(message);
    }
}
