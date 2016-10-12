package com.android.criticos.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.criticos.R;
import com.android.criticos.models.Actor;
import java.util.ArrayList;

/**
 * Created by Daniel on 02/10/2016.
 */

public class RecyclerViewAdapterActors extends RecyclerView.Adapter<RecyclerViewAdapterActors.MyViewHolder> {

    private ArrayList<Actor> actors;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapterActors(Actor actor)
    {
        actors=new ArrayList<>();
        actors.add(actor);
    }

    public RecyclerViewAdapterActors(ArrayList<Actor> actors)
    {
        this.actors=actors;
    }

    public interface OnItemClickListener
    {
        void onLongClickItem(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener
    {
        public ImageView userPic;
        public TextView userNameText, userRolText;

        public MyViewHolder(View view)
        {
            super(view);
            userPic = (ImageView) view.findViewById(R.id.userPic);
            userNameText = (TextView) view.findViewById(R.id.userNameText);
            userRolText = (TextView) view.findViewById(R.id.userRolText);
            view.setTag(view);
            view.setOnLongClickListener(this);
        }
        @Override
        public boolean onLongClick(View view)
        {
            itemClickListener.onLongClickItem(view,getAdapterPosition());
            return false;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Actor actor = actors.get(position);
        holder.userPic.setImageResource(R.mipmap.ic_launcher);
        holder.userNameText.setText(actor.getUser().getUserName()+" "+actor.getUser().getUserLastName());
        holder.userRolText.setText(actor.getRol());
    }

    public void setClickListener(OnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return actors.size();
    }

    public void addActor(Actor actor)
    {
        actors.add(actor);
        this.notifyItemInserted(actors.size()-1);
    }

    public void deleteActor(int position)
    {
        actors.remove(position);
        this.notifyItemRemoved(position);
    }

    public ArrayList<Actor> getActors()
    {
        return actors;
    }
}