package com.android.criticos.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 28/09/2016.
 */
public class User implements Serializable{

    public String userEmail;
    public String userPassword;
    public String userName;
    public String userLastName;
    public String userOccupation;
    public String userPicture;
    public boolean userState;
    public ArrayList<Actor> myEventsCreate;

    public User()
    {

    }
    public User(String userEmail, String userPassword, String userName, String userLastName, String userOccupation)
    {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userOccupation = userOccupation;
    }

    public User(String userEmail, String userPassword, String userName, String userLastName, String userOccupation, String userPicture, boolean userState) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userOccupation = userOccupation;
        this.userPicture = userPicture;
        this.userState = userState;
    }

    public boolean isUserState() {
        return userState;
    }

    public void setUserState(boolean userState) {
        this.userState = userState;
    }

    public ArrayList<Actor> getMyEventsCreate() {
        return myEventsCreate;
    }

    public void setMyEventsCreate(ArrayList<Actor> myEventsCreate) {
        this.myEventsCreate = myEventsCreate;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isUserState() != user.isUserState()) return false;
        if (getUserEmail() != null ? !getUserEmail().equals(user.getUserEmail()) : user.getUserEmail() != null)
            return false;
        if (getUserPassword() != null ? !getUserPassword().equals(user.getUserPassword()) : user.getUserPassword() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(user.getUserName()) : user.getUserName() != null)
            return false;
        if (getUserLastName() != null ? !getUserLastName().equals(user.getUserLastName()) : user.getUserLastName() != null)
            return false;
        if (getUserOccupation() != null ? !getUserOccupation().equals(user.getUserOccupation()) : user.getUserOccupation() != null)
            return false;
        if (getUserPicture() != null ? !getUserPicture().equals(user.getUserPicture()) : user.getUserPicture() != null)
            return false;
        return getMyEventsCreate() != null ? getMyEventsCreate().equals(user.getMyEventsCreate()) : user.getMyEventsCreate() == null;

    }

    @Override
    public int hashCode() {
        int result = getUserEmail() != null ? getUserEmail().hashCode() : 0;
        result = 31 * result + (getUserPassword() != null ? getUserPassword().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getUserLastName() != null ? getUserLastName().hashCode() : 0);
        result = 31 * result + (getUserOccupation() != null ? getUserOccupation().hashCode() : 0);
        result = 31 * result + (getUserPicture() != null ? getUserPicture().hashCode() : 0);
        result = 31 * result + (isUserState() ? 1 : 0);
        result = 31 * result + (getMyEventsCreate() != null ? getMyEventsCreate().hashCode() : 0);
        return result;
    }
}
