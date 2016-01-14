package com.thevarunshah.backend;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.thevarunshah.classes.ChecklistItem;
import com.thevarunshah.notes.ChecklistView;
import com.thevarunshah.notes.R;

import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<ChecklistItem> {

    private final ArrayList<ChecklistItem> list; //the list the adapter manages
    private final Context context; //context attached to adapter

    public static int currActive;

    /**
     * the checklist adapter
     * @param context the application context
     * @param list the list of items
     */
    public ChecklistAdapter(Context context, ArrayList<ChecklistItem> list) {

        super(context, R.layout.checklist_row, list);
        this.context = context;
        this.list = list;
    }

    /**
     * a view holder for each item in the row
     */
    private class ViewHolder {

        CheckBox done;
        EditText text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            //inflate view and link each component to the holder
            LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.checklist_row, null);
            holder.text = (EditText) convertView.findViewById(R.id.checklist_text);
            holder.done = (CheckBox) convertView.findViewById(R.id.checklist_check);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        final ViewHolder holderFinal = holder;
        //attach a check listener to the checkbox
        holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //get item and set as done/undone
                ChecklistItem item = getItem(position);
                item.setDone(isChecked);

                //apply/remove strikethrough effect and disable/enable edit functionality
                if(isChecked){
                    holderFinal.text.setPaintFlags(holderFinal.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holderFinal.text.setFocusable(false);
                }
                else{
                    holderFinal.text.setPaintFlags(holderFinal.text.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    holderFinal.text.setFocusableInTouchMode(true);
                }
            }
        });

        //get item and link references to holder
        ChecklistItem item = list.get(position);
        holder.text.setText(item.getItemText());
        holder.done.setChecked(item.isDone());
        holder.done.setTag(item);

        holder.text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(ChecklistView.exiting){
                        return;
                    }
                    final EditText et = (EditText) v;
                    list.get(position).setItemText(et.getText().toString());
                    ChecklistView.cl.updateDate();
                }
                else{
                    currActive = position;
                }
            }
        });

        return convertView;
    }
}
