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
    private TextView usernumTextView;
    //private TextView maxnumTextView;

    private ArrayList<GroupContent> groupList = new ArrayList<GroupContent>();


    public GroupAdapter() {  }

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
    public Object getItem(int position)
    {
        return groupList.get(position);
    }

    public void addItem(GroupContent GroupInfo)
    {
        groupList.add(GroupInfo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.designed_content_group, parent, false);
        }

        //maxnumTextView = (TextView) convertView.findViewById(R.id.text3);
        gnameTextView = (TextView) convertView.findViewById(R.id.groupNameTextView);
        usernumTextView = (TextView) convertView.findViewById(R.id.memberNumTextView1);

        GroupContent groupContent = groupList.get(position);

        gnameTextView.setText(groupContent.getGname());
        usernumTextView.setText("인원 수 : " + Integer.toString(groupContent.getUserList().size())+" / "+groupContent.getMaxNum()); //유저리스트의 사이즈를 text2에 씀

        return convertView;

    }
}