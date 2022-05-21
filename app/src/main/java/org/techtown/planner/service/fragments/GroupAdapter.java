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

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<GroupContent> sample;

    public GroupAdapter(Context context, ArrayList<GroupContent> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GroupContent getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.fragment_group, null);

        TextView grade = (TextView)view.findViewById(R.id.textview);
        //grade.setText(sample.get(position).getGrade());

        return view;
    }
}