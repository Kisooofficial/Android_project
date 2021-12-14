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
    //제네릭 이용하여 리스트 뷰 사용
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    //메서드 오버라이드
    @Override
    public int getCount() {
        return items.size();
    }

    //position의 아이템 반환
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    //position의 아이템 아이디 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        //리스트뷰 형태에 있던 것들을 Searching_record에 띄울 수 있도록 inflate
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        //listview_item에 설정된 뷰를 가져오도록 findViewById 사용
        TextView nameText = convertView.findViewById(R.id.listViewName);
        TextView typeText = convertView.findViewById(R.id.listViewType);
        TextView distanceText = convertView.findViewById(R.id.listViewDistance);
        TextView averageText = convertView.findViewById(R.id.listViewAverageSpeed);

        //각 요소에 대해 해당하는 데이터를 삽입
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
