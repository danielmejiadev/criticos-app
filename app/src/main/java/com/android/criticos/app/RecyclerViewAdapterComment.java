package com.android.criticos.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.criticos.R;
import com.android.criticos.models.Comment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Daniel on 09/10/2016.
 */
public class RecyclerViewAdapterComment extends RecyclerView.Adapter<RecyclerViewAdapterComment.MyViewHolder>  {

    private ArrayList<Comment> eventComments;


    public RecyclerViewAdapterComment(ArrayList<Comment> eventComments)
    {
        this.eventComments=eventComments;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        EditText eventComment;
        TextView eventDateComment;

        public MyViewHolder(View view)
        {
            super(view);
            eventComment = (EditText) view.findViewById(R.id.eventComment);
            eventDateComment = (TextView)view.findViewById(R.id.eventDateComment);
            view.setTag(view);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Comment comment = eventComments.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        holder.eventComment.setText(comment.getCommentDescription());
        holder.eventDateComment.setText(dateFormat.format(comment.getCommentDate()));
    }


    @Override
    public int getItemCount()
    {
        return eventComments.size();
    }

    public void addComment(Comment comment)
    {
        this.eventComments.add(0,comment);
        this.notifyItemInserted(0);
    }

}
