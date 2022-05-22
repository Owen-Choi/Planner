package org.techtown.planner.service.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.techtown.planner.R;
import org.techtown.planner.domain.Group.GroupContent;

import java.util.ArrayList;

public class GroupAdapter extends BaseAdapter {

    private TextView gnameTextView;


    private ArrayList<GroupContent> groupList = new ArrayList<>();

    public GroupAdapter() {
    }

    @Override
    public int getCount()
    {
        return groupList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public GroupContent getItem(int position)
    {
        return groupList.get(position);
    }

    public void addItem(String gname)
    {
        GroupContent item = new GroupContent();

        item.setName(gname);

        groupList.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_group, parent, false);
        }

        gnameTextView = (TextView) convertView.findViewById(R.id.text1);

        GroupContent groupContent = groupList.get(position);

        gnameTextView.setText(groupContent.getName());

        return convertView;

//        View view = LayoutInflater.inflate(R.layout.fragment_group, null);
//
//        TextView grade = (TextView)view.findViewById(R.id.textview);
//        //grade.setText(sample.get(position).getGrade());
    }


}