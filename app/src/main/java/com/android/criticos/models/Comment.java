package com.android.criticos.models;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Daniel on 08/10/2016.
 */
public class Comment implements Serializable {

    private int commentId;
    private String commentDescription;
    private Date commentDate;
    private Event event;

    public Comment() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
