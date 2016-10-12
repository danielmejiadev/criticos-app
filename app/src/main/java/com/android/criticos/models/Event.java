package com.android.criticos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daniel on 02/10/2016.
 */
public class Event implements Serializable{

    public int eventId;
    public String eventName;
    public String eventDescription;
    public Date eventDate;
    public Date eventBeginTime;
    public Date eventEndTime;
    public String eventPlace;
    public int eventCalification;
    public ArrayList<Actor> eventActors;
    public ArrayList<User> eventAttendings;
    public ArrayList<Comment> eventComments;

    public Event()
    {

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventBeginTime() {
        return eventBeginTime;
    }

    public void setEventBeginTime(Date eventBeginTime) {
        this.eventBeginTime = eventBeginTime;
    }

    public Date getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public int getEventCalification() {
        return eventCalification;
    }

    public void setEventCalification(int eventCalification) {
        this.eventCalification = eventCalification;
    }

    public ArrayList<Actor> getEventActors() {
        return eventActors;
    }

    public void setEventActors(ArrayList<Actor> eventActors) {
        this.eventActors = eventActors;
    }

    public ArrayList<User> getEventAttendings() {
        return eventAttendings;
    }

    public void setEventAttendings(ArrayList<User> eventAttendings) {
        this.eventAttendings = eventAttendings;
    }

    public ArrayList<Comment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(ArrayList<Comment> eventComments) {
        this.eventComments = eventComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (getEventId() != event.getEventId()) return false;
        if (getEventCalification() != event.getEventCalification()) return false;
        if (getEventName() != null ? !getEventName().equals(event.getEventName()) : event.getEventName() != null)
            return false;
        if (getEventDescription() != null ? !getEventDescription().equals(event.getEventDescription()) : event.getEventDescription() != null)
            return false;
        if (getEventDate() != null ? !getEventDate().equals(event.getEventDate()) : event.getEventDate() != null)
            return false;
        if (getEventBeginTime() != null ? !getEventBeginTime().equals(event.getEventBeginTime()) : event.getEventBeginTime() != null)
            return false;
        if (getEventEndTime() != null ? !getEventEndTime().equals(event.getEventEndTime()) : event.getEventEndTime() != null)
            return false;
        if (getEventPlace() != null ? !getEventPlace().equals(event.getEventPlace()) : event.getEventPlace() != null)
            return false;
        if (getEventActors() != null ? !getEventActors().equals(event.getEventActors()) : event.getEventActors() != null)
            return false;
        if (getEventAttendings() != null ? !getEventAttendings().equals(event.getEventAttendings()) : event.getEventAttendings() != null)
            return false;
        return getEventComments() != null ? getEventComments().equals(event.getEventComments()) : event.getEventComments() == null;

    }

    @Override
    public int hashCode() {
        int result = getEventId();
        result = 31 * result + (getEventName() != null ? getEventName().hashCode() : 0);
        result = 31 * result + (getEventDescription() != null ? getEventDescription().hashCode() : 0);
        result = 31 * result + (getEventDate() != null ? getEventDate().hashCode() : 0);
        result = 31 * result + (getEventBeginTime() != null ? getEventBeginTime().hashCode() : 0);
        result = 31 * result + (getEventEndTime() != null ? getEventEndTime().hashCode() : 0);
        result = 31 * result + (getEventPlace() != null ? getEventPlace().hashCode() : 0);
        result = 31 * result + getEventCalification();
        result = 31 * result + (getEventActors() != null ? getEventActors().hashCode() : 0);
        result = 31 * result + (getEventAttendings() != null ? getEventAttendings().hashCode() : 0);
        result = 31 * result + (getEventComments() != null ? getEventComments().hashCode() : 0);
        return result;
    }
}
