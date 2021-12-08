package org.techtown.final_exam_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.textView7);
        TextView typeText = convertView.findViewById(R.id.textView9);
        TextView distanceText = convertView.findViewById(R.id.textView11);
        TextView averageText = convertView.findViewById(R.id.textView13);

        nameText.setText(listItem.getName());
        typeText.setText(listItem.getType());
        distanceText.setText(listItem.getDistance());
        averageText.setText(listItem.getAverage());

        return convertView;

    }

    public void addItem(ListItem item){
        items.add(item);
    }
}
