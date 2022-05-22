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
    //아마 인원수나 비공개 여부인지 정도?

    private ArrayList<GroupContent> groupList = new ArrayList<GroupContent>();

//    int i = GroupContent.getUserList().size();
//    ArrayList a = GroupContent.getUserList();

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

    public void addItem(String gname, ArrayList userList)
    {
        GroupContent item = new GroupContent();

        item.setName(gname);
        item.setUserList(userList);

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
        usernumTextView = (TextView) convertView.findViewById(R.id.text2);

        GroupContent groupContent = groupList.get(position);

        gnameTextView.setText(groupContent.getName());
        usernumTextView.setText("인원 수 : " + Integer.toString(groupContent.getUserList().size())); //유저리스트의 사이즈를 text2에 씀

        return convertView;

    }
}