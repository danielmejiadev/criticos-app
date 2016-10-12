package com.android.criticos.app;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.criticos.R;
import com.android.criticos.models.Event;
import com.android.criticos.presenters.HomePresenter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Daniel on 03/10/2016.
 */
public class ListViewAdapterEvents extends BaseAdapter implements Filterable, View.OnClickListener{

    private ArrayList<Event> eventsList;
    private HomePresenter homePresenter;
    private boolean requestFinished = false;

    public ListViewAdapterEvents(ArrayList<Event> eventsList)
    {
        this.eventsList=eventsList;
    }

    public ListViewAdapterEvents()
    {
        this.eventsList=new ArrayList<>();
    }

    @Override
    public void onClick(View view)
    {

    }

    static class ViewHolderItem
    {
        TextView eventNameText;
        TextView eventDescriptionText;
        TextView eventDateText;
    }

    public int getCount()
    {
        return eventsList.size();
    }

    public Event getItem(int position)
    {
        return this.eventsList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderItem viewHolderItem;
        if(convertView==null)
        {
            viewHolderItem=new ViewHolderItem();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);

            viewHolderItem.eventNameText = (TextView)convertView.findViewById(R.id.eventNameTextRow);
            viewHolderItem.eventDescriptionText = (TextView)convertView.findViewById(R.id.eventDescriptionTextRow);
            viewHolderItem.eventDateText = (TextView)convertView.findViewById(R.id.eventDateTextRow);
            convertView.setTag(viewHolderItem);
        }
        else
        {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }

        Event event = eventsList.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());

        viewHolderItem.eventNameText.setText(event.getEventName());
        viewHolderItem.eventDescriptionText.setText(event.getEventDescription());
        if(event.getEventDate()!=null)
        {
            viewHolderItem.eventDateText.setText(dateFormat.format(event.getEventDate()));
        }
        else
        {
            viewHolderItem.eventDateText.setText("");
        }
        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        return  new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length()>0)
                {
                    homePresenter.searchEventByName(constraint.toString());
                    while(true)
                    {
                        if(requestFinished)
                        {
                            filterResults.values = homePresenter.getEvents();
                            filterResults.count = homePresenter.getEvents().size();
                            requestFinished=false;
                            break;
                        }
                    }

                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    eventsList = (ArrayList<Event>) results.values;
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


    public void setRequestFinished(boolean requestFinished)
    {
        this.requestFinished=requestFinished;
    }

    public void setHomePresenter(HomePresenter homePresenter)
    {
        this.homePresenter=homePresenter;
    }


}
