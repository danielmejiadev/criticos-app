package com.android.criticos.models;

import java.io.Serializable;

/**
 * Created by Daniel on 02/10/2016.
 */
public class Actor implements Serializable{

    public User user;
    public Event event;
    public String rol;
    public int calification;

    public Actor()
    {
    }

    public Actor(User user, Event event, String rol, int calification)
    {
        this.user = user;
        this.event = event;
        this.rol = rol;
        this.calification = calification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getCalification() {
        return calification;
    }

    public void setCalification(int calification) {
        this.calification = calification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (getCalification() != actor.getCalification()) return false;
        if (getUser() != null ? !getUser().equals(actor.getUser()) : actor.getUser() != null)
            return false;
        if (getEvent() != null ? !getEvent().equals(actor.getEvent()) : actor.getEvent() != null)
            return false;
        return getRol() != null ? getRol().equals(actor.getRol()) : actor.getRol() == null;

    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        result = 31 * result + (getRol() != null ? getRol().hashCode() : 0);
        result = 31 * result + getCalification();
        return result;
    }
}
