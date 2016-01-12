package com.thevarunshah.backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.thevarunshah.notes.R;

import java.util.ArrayList;

public class ListNoteAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> list; //the list the adapter manages
    private final Context context; //context attached to adapter

    /**
     * the list adapter
     * @param context the application context
     * @param list the list of items
     */
    public ListNoteAdapter(Context context, ArrayList<String> list) {

        super(context, R.layout.listnote_row, list);
        this.context = context;
        this.list = list;
    }

    /**
     * a view holder for each item in the row
     */
    private class ViewHolder {
        EditText bullet;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            //inflate view and link each component to the holder
            LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listnote_row, null);
            holder.bullet = (EditText) convertView.findViewById(R.id.listnote_bullet);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //get item and link references to holder
        String bulletText = list.get(position);
        holder.bullet.setText(bulletText);

        return convertView;
    }
}
